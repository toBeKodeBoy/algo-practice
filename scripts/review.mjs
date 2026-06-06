import { readFileSync, writeFileSync, existsSync, readdirSync, statSync } from 'fs';
import { join } from 'path';

const ROOT = join(import.meta.dirname, '..');
const REVIEW_DIR = join(ROOT, 'review');
const SCHEDULE_PATH = join(REVIEW_DIR, 'schedule.json');
const README_PATH = join(REVIEW_DIR, 'README.md');
const ALGO_DIR = join(ROOT, 'src', 'main', 'java', 'algo');

const INTERVALS = [
    { round: 'R1', days: 1 },
    { round: 'R2', days: 2 },
    { round: 'R3', days: 4 },
    { round: 'R4', days: 7 },
    { round: 'R5', days: 15 },
    { round: 'R6', days: 30 },
];

function roundIndex(round) {
    return INTERVALS.findIndex(x => x.round === round);
}

/** 方案 A：以完成日为锚点，滚动重算后续未完成轮的 due */
function rescheduleFrom(entry, completedRound, doneDate) {
    const idx = roundIndex(completedRound);
    if (idx < 0) return [];
    let anchor = doneDate;
    const updated = [];
    for (let i = idx + 1; i < entry.reviews.length; i++) {
        const r = entry.reviews[i];
        if (r.done) continue;
        anchor = addDays(anchor, INTERVALS[i].days - INTERVALS[i - 1].days);
        const oldDue = r.due;
        r.due = anchor;
        r.rescheduledFrom = completedRound;
        r.rescheduledOn = doneDate;
        updated.push({ round: r.round, oldDue, newDue: r.due });
    }
    return updated;
}

function findTargetReview(entry, today, { catchUp = false } = {}) {
    const pending = entry.reviews.filter(r => !r.done);
    if (pending.length === 0) return null;
    const sortByRound = (a, b) => roundIndex(a.round) - roundIndex(b.round);
    const dueNow = pending.filter(r => r.due <= today).sort(sortByRound);
    // 多轮逾期时取最高轮，配合 rollup 一次复盘清积压
    if (catchUp && dueNow.length >= 2) return dueNow[dueNow.length - 1];
    if (dueNow.length > 0) return dueNow[0];
    return pending.sort(sortByRound)[0];
}

function rollupBefore(entry, targetRound, doneDate, slot) {
    const targetIdx = roundIndex(targetRound);
    const rolled = [];
    for (let i = 0; i < targetIdx; i++) {
        const r = entry.reviews.find(x => x.round === INTERVALS[i].round);
        if (r && !r.done) {
            r.done = doneDate;
            r.slot = slot || null;
            r.rollup = true;
            rolled.push(r.round);
        }
    }
    return rolled;
}

function nextPendingReview(entry) {
    return entry.reviews.find(r => !r.done) || null;
}

function applyMark(entry, round, doneDate, slot, { rollup = false } = {}) {
    const review = entry.reviews.find(r => r.round === round);
    if (!review) return null;
    review.done = doneDate;
    review.slot = slot || null;
    if (rollup) review.rollup = true;
    const rescheduled = rescheduleFrom(entry, round, doneDate);
    return { review, rescheduled };
}

function addDays(dateStr, days) {
    const d = new Date(dateStr + 'T12:00:00');
    d.setDate(d.getDate() + days);
    return d.toISOString().slice(0, 10);
}

function todayStr() {
    return new Date().toISOString().slice(0, 10);
}

function loadSchedule() {
    if (!existsSync(SCHEDULE_PATH)) return {};
    return JSON.parse(readFileSync(SCHEDULE_PATH, 'utf-8'));
}

function saveSchedule(schedule) {
    writeFileSync(SCHEDULE_PATH, JSON.stringify(schedule, null, 2), 'utf-8');
}

function buildReviews(firstDone) {
    return INTERVALS.map(({ round, days }) => ({
        round,
        due: addDays(firstDone, days),
        done: null,
        slot: null,
    }));
}

function parseArgs(argv) {
    const args = { _: [] };
    for (let i = 0; i < argv.length; i++) {
        const a = argv[i];
        if (a.startsWith('--')) {
            const key = a.slice(2);
            const val = argv[i + 1] && !argv[i + 1].startsWith('--') ? argv[++i] : true;
            args[key] = val;
        } else {
            args._.push(a);
        }
    }
    return args;
}

function extractLcId(filename) {
    const m = filename.match(/LC_(\d{4})_/);
    return m ? `LC_${m[1]}` : null;
}

function scanSolutions() {
    const found = [];
    if (!existsSync(ALGO_DIR)) return found;
    for (const year of readdirSync(ALGO_DIR)) {
        const yearPath = join(ALGO_DIR, year);
        if (!statSync(yearPath).isDirectory() || !/^y?\d{4}$/.test(year)) continue;
        const yearNum = year.replace(/^y/, '');
        for (const month of readdirSync(yearPath)) {
            const monthPath = join(yearPath, month);
            if (!statSync(monthPath).isDirectory() || !/^M?\d{2}$/.test(month)) continue;
            const monthNum = month.replace(/^M/, '');
            for (const day of readdirSync(monthPath)) {
                const dayPath = join(monthPath, day);
                if (!statSync(dayPath).isDirectory() || !/^d?\d{2}$/.test(day)) continue;
                const dayNum = day.replace(/^d/, '');
                const date = `${yearNum}-${monthNum}-${dayNum}`;
                for (const file of readdirSync(dayPath).filter(f => f.endsWith('.java') && !f.endsWith('Test.java'))) {
                    const content = readFileSync(join(dayPath, file), 'utf-8');
                    const lcId = extractLcId(file);
                    if (!lcId) continue;
                    const titleLine = content.split('\n').find(l => l.startsWith('// 题目'));
                    const title = titleLine ? titleLine.replace(/^\/\/ 题目\s+/, '').trim() : file;
                    const pitfalls = [];
                    const pitfallLine = content.split('\n').find(l => l.includes('易错'));
                    if (pitfallLine) {
                        const m = pitfallLine.match(/易错[:\s]+(.+)/);
                        if (m) pitfalls.push(m[1].trim());
                    }
                    found.push({ lcId, title, date, file, pitfalls });
                }
            }
        }
    }
    return found;
}

function cmdAdd(args) {
    const lcId = args._[1];
    if (!lcId) {
        console.error('Usage: node scripts/review.mjs add LC_0003 --date 2026-06-04 --title "..."');
        process.exit(1);
    }
    const schedule = loadSchedule();
    const firstDone = args.date || todayStr();
    const title = args.title || lcId;
    const pitfalls = args.pitfalls ? args.pitfalls.split(',').map(s => s.trim()) : [];
    schedule[lcId] = {
        title,
        firstDone,
        reviews: buildReviews(firstDone),
        pitfalls,
    };
    saveSchedule(schedule);
    console.log(`✅ Added ${lcId} (${title}), firstDone=${firstDone}`);
}

function cmdMark(args) {
    const lcId = args._[1];
    const round = args.round;
    if (!lcId || !round) {
        console.error('Usage: node scripts/review.mjs mark LC_0003 --round R4 --date 2026-06-11 --slot 晚地铁');
        process.exit(1);
    }
    const schedule = loadSchedule();
    const entry = schedule[lcId];
    if (!entry) {
        console.error(`❌ ${lcId} not found in schedule`);
        process.exit(1);
    }
    const review = entry.reviews.find(r => r.round === round);
    if (!review) {
        console.error(`❌ Round ${round} not found for ${lcId}`);
        process.exit(1);
    }
    const doneDate = args.date || todayStr();
    const result = applyMark(entry, round, doneDate, args.slot || null);
    saveSchedule(schedule);
    console.log(`✅ Marked ${lcId} ${round} done on ${result.review.done}`);
    for (const u of result.rescheduled) {
        console.log(`   ↳ ${u.round} due ${u.oldDue} → ${u.newDue}`);
    }
}

/** 方案 C：复盘专用，自动选轮 + 合并逾期 + 滚动排期 */
function cmdReview(args) {
    const lcId = args._[1];
    if (!lcId) {
        console.error('Usage: node scripts/review.mjs review LC_0001 --slot 晚地铁 [--date 2026-06-06] [--no-rollup]');
        process.exit(1);
    }
    const schedule = loadSchedule();
    const entry = schedule[lcId];
    if (!entry) {
        console.error(`❌ ${lcId} not found in schedule`);
        process.exit(1);
    }
    const doneDate = args.date || todayStr();
    const slot = args.slot || null;
    const useRollup = args['no-rollup'] !== true;

    const overdueCount = entry.reviews.filter(r => !r.done && r.due <= doneDate).length;
    const target = findTargetReview(entry, doneDate, { catchUp: useRollup && overdueCount >= 2 });
    if (!target) {
        console.log(`✅ ${lcId} 全部复习轮次已完成`);
        return;
    }

    let rolled = [];
    if (useRollup) {
        rolled = rollupBefore(entry, target.round, doneDate, slot);
    }

    const result = applyMark(entry, target.round, doneDate, slot);
    saveSchedule(schedule);
    generateReviewReadme(schedule);

    console.log(`✅ 复盘 ${lcId} [${target.round}] @ ${doneDate}${slot ? ` · ${slot}` : ''}`);
    if (rolled.length) {
        console.log(`   合并逾期轮次: ${rolled.join(', ')}`);
    }
    for (const u of result.rescheduled) {
        console.log(`   ↳ ${u.round} due ${u.oldDue} → ${u.newDue}`);
    }
    const next = nextPendingReview(entry);
    if (next) {
        console.log(`   下次复习: ${next.round} @ ${next.due}`);
    } else {
        console.log('   本题复习计划已全部完成');
    }
}

function getReviewStatus(entry, today) {
    const pending = entry.reviews.filter(r => !r.done);
    if (pending.length === 0) return '已完成';
    const overdue = pending.some(r => r.due < today);
    if (overdue) return '逾期';
    const dueToday = pending.some(r => r.due === today);
    if (dueToday) return '待复习';
    return '进行中';
}

function loadHot100() {
    const path = join(ROOT, 'review', 'hot100.json');
    if (!existsSync(path)) return null;
    return JSON.parse(readFileSync(path, 'utf-8'));
}

function diffDays(a, b) {
    return Math.floor((new Date(a + 'T12:00:00') - new Date(b + 'T12:00:00')) / (1000 * 60 * 60 * 24));
}

function cmdDaily() {
    const today = todayStr();
    const schedule = loadSchedule();
    const solutions = scanSolutions();
    const hot100 = loadHot100();
    if (!hot100) {
        console.error('❌ review/hot100.json not found');
        process.exit(1);
    }

    const doneLcIds = new Set(solutions.map(s => s.lcId));

    // Find current phase and next undone problem
    let currentPhase = null;
    let nextProblem = null;
    for (const p of hot100.phases) {
        const undone = p.problems.filter(id => !doneLcIds.has(`LC_${String(id).padStart(4, '0')}`));
        if (undone.length > 0) {
            currentPhase = p;
            const id = undone[0];
            nextProblem = { lcId: `LC_${String(id).padStart(4, '0')}`, num: id, title: hot100.titles[String(id)] || '' };
            break;
        }
    }
    if (!currentPhase) currentPhase = hot100.phases[hot100.phases.length - 1];

    // Collect reviews
    const dueToday = [];
    const overdue = [];
    for (const [lcId, entry] of Object.entries(schedule)) {
        for (const r of entry.reviews) {
            if (r.done) continue;
            if (r.due === today) dueToday.push({ lcId, title: entry.title, ...r });
            else if (r.due < today) overdue.push({ lcId, title: entry.title, ...r });
        }
    }
    overdue.sort((a, b) => a.due.localeCompare(b.due));

    // Group overdue by problem
    const overdueByProblem = {};
    for (const r of overdue) {
        if (!overdueByProblem[r.lcId]) overdueByProblem[r.lcId] = { title: r.title, rounds: [], maxDays: 0 };
        overdueByProblem[r.lcId].rounds.push(r.round);
        const d = diffDays(today, r.due);
        if (d > overdueByProblem[r.lcId].maxDays) overdueByProblem[r.lcId].maxDays = d;
    }
    const sortedOverdue = Object.entries(overdueByProblem).sort((a, b) => b[1].maxDays - a[1].maxDays);

    // Date directory path
    const m = today.slice(0, 4), m2 = today.slice(5, 7), d2 = today.slice(8, 10);
    const dayPath = `src\\main\\java\\algo\\y${m}\\M${m2}\\d${d2}`;

    // ─── output ──────────────────────────────────────────
    console.log(`\n📋 今日计划 (${today})`);
    console.log('');

    // ── early metro ──
    console.log('━━ 早地铁 · 新题 ━━');
    if (nextProblem) {
        const phaseInfo = `${currentPhase.id} ${currentPhase.name}`;
        console.log(`  ${nextProblem.lcId} ${nextProblem.title}`);
        console.log(`  阶段: ${phaseInfo} (剩余 ${currentPhase.problems.filter(id => !doneLcIds.has(`LC_${String(id).padStart(4, '0')}`)).length} 题)`);
        console.log(`  copy template\\Solution.java ${dayPath}\\${nextProblem.lcId}_....java`);
    } else {
        console.log('  ✅ 所有阶段已完成！');
    }
    console.log('');

    // ── late metro ──
    console.log('━━ 晚地铁 · 复习 ━━');
    if (dueToday.length > 0) {
        console.log(`  今日到期 (${dueToday.length}):`);
        for (const r of dueToday) {
            console.log(`  ${r.lcId} ${r.title} [${r.round}]`);
        }
    } else {
        console.log('  今日无到期');
    }
    if (overdue.length > 0) {
        console.log(`  逾期: ${overdue.length} 条, ${sortedOverdue.length} 题`);
        console.log('  逾期最多:');
        for (const [lcId, info] of sortedOverdue.slice(0, 3)) {
            console.log(`    ${lcId} ${info.title} [${info.rounds.join('/')}] 逾期 ${info.maxDays} 天`);
        }
    }
    console.log('');

    // ── quick cmds ──
    console.log('━━ 打卡命令 ━━');
    for (const r of dueToday.slice(0, 2)) {
        console.log(`  node scripts/review.mjs review ${r.lcId} --slot 晚地铁`);
    }
    if (sortedOverdue.length > 0 && dueToday.length < 2) {
        for (const [lcId] of sortedOverdue.slice(0, 2 - dueToday.length)) {
            console.log(`  node scripts/review.mjs review ${lcId} --slot 晚地铁`);
        }
    }
    console.log('  .\\scripts\\mvn.ps1 test');
    console.log('  node scripts/stats.mjs');
    console.log('  node scripts/review.mjs sync');
    console.log('');
}

function cmdToday() {
    const schedule = loadSchedule();
    const today = todayStr();
    const due = [];
    const overdue = [];
    for (const [lcId, entry] of Object.entries(schedule)) {
        for (const r of entry.reviews) {
            if (r.done) continue;
            if (r.due < today) overdue.push({ lcId, title: entry.title, ...r });
            else if (r.due === today) due.push({ lcId, title: entry.title, ...r });
        }
    }
    console.log(`📅 今日复习清单 (${today})\n`);
    if (due.length === 0 && overdue.length === 0) {
        console.log('   无到期复习，可做新题或预习。');
        return;
    }
    if (overdue.length) {
        console.log('⚠️  逾期:');
        for (const r of overdue) console.log(`   ${r.lcId} ${r.title} [${r.round}] 应于 ${r.due}`);
        console.log('');
    }
    if (due.length) {
        console.log('🔔 今日到期:');
        for (const r of due) console.log(`   ${r.lcId} ${r.title} [${r.round}]`);
    }
}

function generateReviewReadme(schedule) {
    const today = todayStr();
    const entries = Object.entries(schedule).sort((a, b) => a[1].firstDone.localeCompare(b[1].firstDone));

    let md = `# 艾宾浩斯复习表

> 自动生成于 ${today} | 间隔：+1 / +2 / +4 / +7 / +15 / +30 天

---

## 今日到期

`;
    const dueToday = [];
    const overdue = [];
    for (const [lcId, entry] of entries) {
        for (const r of entry.reviews) {
            if (r.done) continue;
            if (r.due === today) dueToday.push({ lcId, entry, r });
            if (r.due < today) overdue.push({ lcId, entry, r });
        }
    }
    if (dueToday.length === 0 && overdue.length === 0) {
        md += '无到期复习。\n\n';
    } else {
        if (overdue.length) {
            md += '### 逾期\n\n| 题号 | 题目 | 轮次 | 应复习日 |\n|------|------|:----:|----------|\n';
            for (const { lcId, entry, r } of overdue) {
                md += `| ${lcId} | ${entry.title} | ${r.round} | ${r.due} |\n`;
            }
            md += '\n';
        }
        if (dueToday.length) {
            md += '### 今日\n\n| 题号 | 题目 | 轮次 |\n|------|------|:----:|\n';
            for (const { lcId, entry, r } of dueToday) {
                md += `| ${lcId} | ${entry.title} | ${r.round} |\n`;
            }
            md += '\n';
        }
    }

    md += `## 全量复习计划

| 题号 | 题目 | 初刷 | R1(+1) | R2(+2) | R3(+4) | R4(+7) | R5(+15) | R6(+30) | 状态 |
|------|------|------|--------|--------|--------|--------|---------|---------|------|
`;

    for (const [lcId, entry] of entries) {
        const cols = INTERVALS.map(({ round }) => {
            const r = entry.reviews.find(x => x.round === round);
            if (!r) return '-';
            if (r.done) return `✅${r.done.slice(5)}`;
            if (r.due < today) return `⚠️${r.due.slice(5)}`;
            if (r.due === today) return `🔔${r.due.slice(5)}`;
            return r.due.slice(5);
        });
        const status = getReviewStatus(entry, today);
        md += `| ${lcId} | ${entry.title} | ${entry.firstDone} | ${cols.join(' | ')} | ${status} |\n`;
    }

    md += `
---

## 命令

\`\`\`bash
node scripts/review.mjs daily          # 今日计划（新题 + 复习）
node scripts/review.mjs review LC_0001 --slot 晚地铁   # 复盘（自动选轮 + 滚动排期）
node scripts/review.mjs mark LC_0003 --round R4 --date 2026-06-11 --slot 晚地铁
node scripts/review.mjs add LC_0049 --date 2026-06-06 --title "字母异位词分组"
node scripts/review.mjs sync           # 从题解扫描并同步 schedule
\`\`\`
`;
    writeFileSync(README_PATH, md, 'utf-8');
    console.log(`   Saved ${README_PATH}`);
}

function cmdSync() {
    const schedule = loadSchedule();
    const solutions = scanSolutions();
    const byLc = {};
    for (const s of solutions) {
        if (!byLc[s.lcId] || s.date < byLc[s.lcId].date) byLc[s.lcId] = s;
    }
    let added = 0;
    for (const [lcId, s] of Object.entries(byLc)) {
        if (!schedule[lcId]) {
            schedule[lcId] = {
                title: s.title,
                firstDone: s.date,
                reviews: buildReviews(s.date),
                pitfalls: s.pitfalls,
            };
            added++;
        }
    }
    saveSchedule(schedule);
    generateReviewReadme(schedule);
    console.log(`✅ Synced schedule: ${Object.keys(schedule).length} entries (${added} new)`);
}

function cmdGenerate() {
    generateReviewReadme(loadSchedule());
    console.log('✅ Generated review/README.md');
}

function main() {
    const args = parseArgs(process.argv.slice(2));
    const cmd = args._[0] || 'sync';
    switch (cmd) {
        case 'add': cmdAdd(args); break;
        case 'mark': cmdMark(args); break;
        case 'review': cmdReview(args); break;
        case 'today': cmdToday(); break;
        case 'daily': cmdDaily(); break;
        case 'sync': cmdSync(); break;
        case 'generate': cmdGenerate(); break;
        default:
            console.error(`Unknown command: ${cmd}`);
            process.exit(1);
    }
}

main();
