import { readFileSync, writeFileSync, readdirSync, existsSync, statSync } from 'fs';
import { join, sep } from 'path';

const ROOT = join(import.meta.dirname, '..');
const ALGO_DIR = join(ROOT, 'src', 'main', 'java', 'algo');
const STATS_DIR = join(ROOT, 'stats');
const SCHEDULE_PATH = join(ROOT, 'review', 'schedule.json');

// ─── helpers ────────────────────────────────────────────────

function parseSolutionMeta(filePath) {
    const content = readFileSync(filePath, 'utf-8');
    const lines = content.split('\n').filter(l => l.startsWith('// '));
    const meta = {};
    for (const line of lines) {
        const text = line.replace(/^\/\/ /, '');
        // — old template format —
        if (text.startsWith('LeetCode ')) {
            const m = text.match(/LeetCode\s+(\d+)\s*-\s*(.+)/);
            if (m) { meta.id = m[1]; meta.title = m[2].trim(); }
        } else if (text.startsWith('难度:')) {
            meta.difficulty = text.replace('难度:', '').trim().split('/')[0].trim();
        } else if (text.startsWith('标签:')) {
            meta.tags = text.replace('标签:', '').trim().split(',').map(s => s.trim());
        } else if (text.startsWith('时间复杂度:')) {
            meta.timeComplexity = text.replace('时间复杂度:', '').trim();
        } else if (text.startsWith('空间复杂度:')) {
            meta.spaceComplexity = text.replace('空间复杂度:', '').trim();
        // — new template format —
        } else if (text.startsWith('题目 ')) {
            meta.title = text.replace(/^题目\s+/, '').trim();
        } else if (text.startsWith('难度 ')) {
            meta.difficulty = text.replace(/^难度\s+/, '').split('/')[0].trim();
        } else if (text.startsWith('标签 ')) {
            meta.tags = text.replace(/^标签\s+/, '').split(',').map(s => s.trim());
        } else if (text.startsWith('时间 ')) {
            const m = text.match(/^时间\s+(.+?)\s+空间\s+(.+)/);
            if (m) { meta.timeComplexity = m[1].trim(); meta.spaceComplexity = m[2].trim(); }
        } else if (text.startsWith('时段 ')) {
            meta.slot = text.replace(/^时段\s+/, '').trim();
        } else if (text.startsWith('类型 ')) {
            meta.type = text.replace(/^类型\s+/, '').trim();
        } else if (text.startsWith('leetcode ')) {
            const m = text.match(/problems\/([^/]+)/);
            if (m) meta.slug = m[1];
        }
    }
    const idMatch = filePath.match(/LC_(\d{4})_/);
    if (idMatch) meta.lcNum = parseInt(idMatch[1], 10);
    return meta;
}

function collectSolutions() {
    const solutions = [];
    if (!existsSync(ALGO_DIR)) return solutions;
    const years = readdirSync(ALGO_DIR);
    for (const year of years) {
        const yearPath = join(ALGO_DIR, year);
        if (!statSync(yearPath).isDirectory() || !/^y?\d{4}$/.test(year)) continue;
        const yearNum = year.replace(/^y/, '');
        const months = readdirSync(yearPath);
        for (const month of months) {
            const monthPath = join(yearPath, month);
            if (!statSync(monthPath).isDirectory() || !/^M?\d{2}$/.test(month)) continue;
            const monthNum = month.replace(/^M/, '');
            const days = readdirSync(monthPath);
            for (const day of days) {
                const dayPath = join(monthPath, day);
                if (!statSync(dayPath).isDirectory() || !/^d?\d{2}$/.test(day)) continue;
                const dayNum = day.replace(/^d/, '');
                const files = readdirSync(dayPath).filter(f => f.endsWith('.java') && !f.endsWith('Test.java'));
                for (const file of files) {
                    const filePath = join(dayPath, file);
                    const meta = parseSolutionMeta(filePath);
                    const date = `${yearNum}-${monthNum}-${dayNum}`;
                    solutions.push({ date, year: yearNum, month: monthNum, day: dayNum, file, filePath, ...meta });
                }
            }
        }
    }
    return solutions;
}

function buildCalendarMap(solutions) {
    const map = {};
    for (const s of solutions) {
        map[s.date] = (map[s.date] || 0) + 1;
    }
    return map;
}

function buildDifficultyStats(solutions) {
    const stats = {};
    for (const s of solutions) {
        const d = s.difficulty || 'Unknown';
        stats[d] = (stats[d] || 0) + 1;
    }
    return stats;
}

function buildTagStats(solutions) {
    const stats = {};
    for (const s of solutions) {
        if (s.tags) {
            for (const tag of s.tags) {
                stats[tag] = (stats[tag] || 0) + 1;
            }
        }
    }
    return stats;
}

function buildSlotStats(solutions) {
    const stats = {
        '早地铁': { 新题: 0, 复习: 0 },
        '晚地铁': { 新题: 0, 复习: 0 },
    };
    for (const s of solutions) {
        const slot = s.slot || '未标注';
        const type = s.type || '新题';
        if (!stats[slot]) stats[slot] = { 新题: 0, 复习: 0 };
        if (type.includes('复习')) stats[slot].复习++;
        else stats[slot].新题++;
    }
    return stats;
}

function loadReviewSchedule() {
    if (!existsSync(SCHEDULE_PATH)) return {};
    return JSON.parse(readFileSync(SCHEDULE_PATH, 'utf-8'));
}

function collectReviewDone(schedule) {
    const byDate = {};
    for (const [lcId, entry] of Object.entries(schedule)) {
        for (const r of entry.reviews || []) {
            if (!r.done) continue;
            if (!byDate[r.done]) byDate[r.done] = [];
            byDate[r.done].push({
                lcId,
                title: entry.title,
                round: r.round,
                slot: r.slot || '未标注',
            });
        }
    }
    return byDate;
}

function emptySlotBucket() {
    return { 新题: 0, 复习: 0, items: [] };
}

function buildDailyCheckins(solutions, reviewByDate) {
    const byDate = {};

    for (const s of solutions) {
        if (!byDate[s.date]) {
            byDate[s.date] = {
                date: s.date,
                checkedIn: true,
                problems: [],
                reviews: [],
                slots: {
                    '早地铁': emptySlotBucket(),
                    '晚地铁': emptySlotBucket(),
                    '未标注': emptySlotBucket(),
                },
            };
        }
        const day = byDate[s.date];
        const lcMatch = s.file.match(/LC_(\d{4})_/);
        const lcId = lcMatch ? `LC_${lcMatch[1]}` : s.file.replace('.java', '');
        const slot = s.slot || '未标注';
        const type = (s.type || '新题').includes('复习') ? '复习' : '新题';
        if (!day.slots[slot]) day.slots[slot] = emptySlotBucket();

        const item = {
            lcId,
            title: s.title || lcId,
            difficulty: s.difficulty || '-',
            slot,
            type,
            file: s.file,
        };
        day.problems.push(item);
        day.slots[slot][type]++;
        day.slots[slot].items.push(item);
    }

    for (const [date, reviews] of Object.entries(reviewByDate)) {
        if (!byDate[date]) {
            byDate[date] = {
                date,
                checkedIn: true,
                problems: [],
                reviews: [],
                slots: {
                    '早地铁': emptySlotBucket(),
                    '晚地铁': emptySlotBucket(),
                    '未标注': emptySlotBucket(),
                },
            };
        }
        const day = byDate[date];
        for (const r of reviews) {
            day.reviews.push(r);
            const slot = r.slot || '未标注';
            if (!day.slots[slot]) day.slots[slot] = emptySlotBucket();
            day.slots[slot].复习++;
            day.slots[slot].items.push({
                lcId: r.lcId,
                title: r.title,
                difficulty: '-',
                slot,
                type: '复习',
                round: r.round,
            });
        }
    }

    for (const day of Object.values(byDate)) {
        const newCount = day.problems.filter(p => p.type === '新题').length;
        const reviewFromProblems = day.problems.filter(p => p.type === '复习').length;
        day.newCount = newCount;
        day.reviewCount = reviewFromProblems + day.reviews.length;
        day.total = day.problems.length + day.reviews.length;
        day.summary = {
            早地铁: { 新题: day.slots['早地铁'].新题, 复习: day.slots['早地铁'].复习 },
            晚地铁: { 新题: day.slots['晚地铁'].新题, 复习: day.slots['晚地铁'].复习 },
        };
    }

    return Object.fromEntries(
        Object.entries(byDate).sort((a, b) => b[0].localeCompare(a[0]))
    );
}

function formatCheckinItem(item) {
    const round = item.round ? ` ${item.round}` : '';
    const diff = item.difficulty && item.difficulty !== '-' ? ` (${item.difficulty})` : '';
    return `${item.lcId} ${item.title}${diff}${round}`;
}

function generateCheckinMarkdown(checkins) {
    const today = new Date().toISOString().slice(0, 10);
    const entries = Object.values(checkins).sort((a, b) => b.date.localeCompare(a.date));
    const totalDays = entries.length;
    const totalProblems = entries.reduce((n, d) => n + d.problems.length, 0);
    const totalReviews = entries.reduce((n, d) => n + d.reviewCount, 0);

    let md = `# 每日打卡记录

> 自动生成于 ${today} | 累计打卡 **${totalDays}** 天 | 新题 **${totalProblems}** | 复习 **${totalReviews}** 次

---

## 打卡月历

`;

    const byMonth = {};
    for (const day of entries) {
        const month = day.date.slice(0, 7);
        if (!byMonth[month]) byMonth[month] = [];
        byMonth[month].push(day);
    }

    for (const month of Object.keys(byMonth).sort((a, b) => b.localeCompare(a))) {
        md += `### ${month}\n\n`;
        md += `| 日期 | 打卡 | 合计 | 新题 | 复习 | 早地铁 | 晚地铁 |\n`;
        md += `|------|:----:|:----:|:----:|:----:|:------:|:------:|\n`;
        for (const day of byMonth[month].sort((a, b) => b.date.localeCompare(a.date))) {
            const am = day.summary.早地铁;
            const pm = day.summary.晚地铁;
            const amStr = am.新题 || am.复习 ? `${am.新题}新/${am.复习}复` : '-';
            const pmStr = pm.新题 || pm.复习 ? `${pm.新题}新/${pm.复习}复` : '-';
            md += `| ${day.date} | ✅ | ${day.total} | ${day.newCount} | ${day.reviewCount} | ${amStr} | ${pmStr} |\n`;
        }
        md += '\n';
    }

    md += `---\n\n## 每日明细\n\n`;
    for (const day of entries) {
        md += `### ${day.date} ✅\n\n`;
        md += `| 指标 | 数值 |\n|------|------|\n`;
        md += `| 合计 | ${day.total} |\n`;
        md += `| 新题 | ${day.newCount} |\n`;
        md += `| 复习 | ${day.reviewCount} |\n\n`;

        for (const slot of ['早地铁', '晚地铁', '未标注']) {
            const bucket = day.slots[slot];
            if (!bucket || bucket.items.length === 0) continue;
            md += `**${slot}**\n\n`;
            for (const item of bucket.items) {
                md += `- [${item.type}] ${formatCheckinItem(item)}\n`;
            }
            md += '\n';
        }
    }

    md += `---\n\n> 运行 \`node scripts/stats.mjs\` 自动更新\n`;
    return md;
}

function buildWeeklyCheckin(calendar) {
    const today = new Date();
    let daysWithRecord = 0;
    for (let i = 0; i < 7; i++) {
        const d = new Date(today);
        d.setDate(d.getDate() - i);
        const key = d.toISOString().slice(0, 10);
        if (calendar[key] > 0) daysWithRecord++;
    }
    return { daysWithRecord, target: 5, met: daysWithRecord >= 5 };
}

function loadHot100() {
    const hotPath = join(import.meta.dirname, '..', 'review', 'hot100.json');
    if (!existsSync(hotPath)) return null;
    return JSON.parse(readFileSync(hotPath, 'utf-8'));
}

function buildProgress(solutions) {
    const hot = loadHot100();
    if (!hot) return null;
    const doneIds = new Set(solutions.map(s => s.lcNum).filter(Boolean));
    const phaseProgress = hot.phases.map(p => {
        const done = p.problems.filter(id => doneIds.has(id)).length;
        return { id: p.id, name: p.name, total: p.problems.length, done, remaining: p.problems.length - done };
    });
    const totalDone = doneIds.size;
    const total = Object.keys(hot.titles).length;
    const p1Remaining = (hot.p1Remaining || []).filter(id => !doneIds.has(id));
    let currentPhase = 'P1';
    for (const p of phaseProgress) {
        if (p.remaining > 0) { currentPhase = p.id; break; }
    }
    return { total, totalDone, percent: Math.round((totalDone / total) * 100), phaseProgress, p1Remaining, currentPhase, hot };
}

function generateProgressMarkdown(progress) {
    if (!progress) return '';
    const { total, totalDone, percent, phaseProgress, p1Remaining, currentPhase, hot } = progress;
    let md = `## Hot 100 进度

| 指标 | 数值 |
|------|------|
| 总进度 | **${totalDone}/${total}** (${percent}%) |
| 当前阶段 | **${currentPhase}** |

### 分阶段

| 阶段 | 专题 | 已完成 | 总数 | 进度 |
|:----:|------|:------:|:----:|:----:|
`;
    for (const p of phaseProgress) {
        const pct = Math.round((p.done / p.total) * 100);
        md += `| ${p.id} | ${p.name} | ${p.done} | ${p.total} | ${pct}% |\n`;
    }
    if (p1Remaining.length) {
        md += `\n### P1 待做（优先）\n\n`;
        for (const id of p1Remaining) {
            md += `- LC ${id} ${hot.titles[String(id)] || ''}\n`;
        }
    }
    md += '\n';
    return md;
}

function updateProgressInReadme(progressMd) {
    const readmePath = join(import.meta.dirname, '..', 'README.md');
    let content = readFileSync(readmePath, 'utf-8');
    const startMarker = '<!-- PROGRESS_START -->';
    const endMarker = '<!-- PROGRESS_END -->';
    if (content.indexOf(startMarker) === -1) {
        const insertBefore = '## 题解记录';
        const idx = content.indexOf(insertBefore);
        if (idx !== -1) {
            content = content.slice(0, idx) + `${startMarker}\n\n${progressMd}${endMarker}\n\n` + content.slice(idx);
        }
    } else {
        const startIdx = content.indexOf(startMarker);
        const endIdx = content.indexOf(endMarker);
        content = content.slice(0, startIdx + startMarker.length) + `\n\n${progressMd}\n` + content.slice(endIdx);
    }
    writeFileSync(readmePath, content, 'utf-8');
    console.log('   Updated progress in README.md');
}

function buildMonthlyStats(solutions) {
    const monthly = {};
    for (const s of solutions) {
        const key = `${s.year}-${s.month}`;
        if (!monthly[key]) monthly[key] = { count: 0, days: new Set(), difficulty: {} };
        monthly[key].count++;
        monthly[key].days.add(s.date);
        const d = s.difficulty || 'Unknown';
        monthly[key].difficulty[d] = (monthly[key].difficulty[d] || 0) + 1;
    }
    for (const key of Object.keys(monthly)) {
        monthly[key].dayCount = monthly[key].days.size;
        delete monthly[key].days;
    }
    return monthly;
}

function computeStreak(calendar) {
    const dates = Object.keys(calendar).sort();
    if (dates.length === 0) return { current: 0, max: 0 };
    let maxStreak = 0, currentStreak = 1;
    for (let i = 1; i < dates.length; i++) {
        const prev = new Date(dates[i - 1]);
        const curr = new Date(dates[i]);
        const diffDays = (curr - prev) / (1000 * 60 * 60 * 24);
        if (diffDays === 1) {
            currentStreak++;
        } else {
            maxStreak = Math.max(maxStreak, currentStreak);
            currentStreak = 1;
        }
    }
    maxStreak = Math.max(maxStreak, currentStreak);
    // current streak: count backwards from today
    const today = new Date();
    const todayStr = today.toISOString().slice(0, 10);
    let streak = 0;
    for (let i = dates.length - 1; i >= 0; i--) {
        const d = new Date(dates[i]);
        const expected = new Date(todayStr);
        expected.setDate(expected.getDate() - (dates.length - 1 - i));
        if (d.toISOString().slice(0, 10) === expected.toISOString().slice(0, 10)) {
            streak++;
        } else {
            break;
        }
    }
    return { current: streak, max: maxStreak };
}

function generateHeatmapSVG(calendar) {
    const now = new Date();
    const oneYearAgo = new Date(now);
    oneYearAgo.setFullYear(now.getFullYear() - 1);

    const days = [];
    for (let d = new Date(oneYearAgo); d <= now; d.setDate(d.getDate() + 1)) {
        days.push(new Date(d));
    }

    // GitHub-style levels: 0,1,2,3,4 based on count
    const values = Object.values(calendar);
    const maxVal = Math.max(...values, 1);

    const cellSize = 13;
    const gap = 2;
    const leftOffset = 52;
    const rightPadding = 16;
    const numCols = Math.ceil(days.length / 7);
    const svgWidth = leftOffset + numCols * (cellSize + gap) + rightPadding;

    let svg = `<svg xmlns="http://www.w3.org/2000/svg" width="${svgWidth}" height="148" viewBox="0 0 ${svgWidth} 148">
  <style>
    .day { outline: 1px solid rgba(0,0,0,0.04); outline-offset: -1px; }
    .day:hover { stroke: #000; stroke-width: 1px; }
  </style>
  <rect width="${svgWidth}" height="148" fill="#fff" rx="8"/>
  <text x="16" y="24" font-size="14" font-weight="bold" fill="#333">📅 过去一年打卡热力图</text>
`;

    const weekDays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    for (let i = 0; i < 7; i++) {
        svg += `  <text x="16" y="${52 + i * 15}" font-size="10" fill="#aaa">${weekDays[i]}</text>\n`;
    }

    for (let i = 0; i < days.length; i++) {
        const d = days[i];
        const dateStr = d.toISOString().slice(0, 10);
        const count = calendar[dateStr] || 0;
        const level = count === 0 ? 0 : Math.min(Math.ceil((count / maxVal) * 4), 4);
        const col = Math.floor(i / 7);
        const row = i % 7;
        const x = leftOffset + col * (cellSize + gap);
        const y = 40 + row * (cellSize + gap);
        const colors = ['#ebedf0', '#9be9a8', '#40c463', '#30a14e', '#216e39'];
        svg += `  <rect class="day" x="${x}" y="${y}" width="${cellSize}" height="${cellSize}" rx="2" fill="${colors[level]}" data-date="${dateStr}" data-count="${count}"/>\n`;
    }

    svg += '</svg>';
    return svg;
}

function generateMarkdown(solutions) {
    const total = solutions.length;
    const calendar = buildCalendarMap(solutions);
    const diffStats = buildDifficultyStats(solutions);
    const tagStats = buildTagStats(solutions);
    const monthly = buildMonthlyStats(solutions);
    const streak = computeStreak(calendar);
    const heatmapSVG = generateHeatmapSVG(calendar);
    const slotStats = buildSlotStats(solutions);
    const weekly = buildWeeklyCheckin(calendar);

    let md = `# 📊 算法练习统计面板

> 自动生成于 ${new Date().toISOString().slice(0, 10)}

---

## 🔥 总览

| 指标 | 数值 |
|------|------|
| 总题数 | **${total}** |
| 连续打卡 | **${streak.current} 天** |
| 最长连续 | **${streak.max} 天** |
| 打卡天数 | **${Object.keys(calendar).length} 天** |

## 📈 难度分布

| 难度 | 数量 |
|------|:----:|
`;

    for (const [diff, count] of Object.entries(diffStats).sort((a, b) => b[1] - a[1])) {
        md += `| ${diff} | ${count} |\n`;
    }

    md += `
## 🏷️ 标签分布

| 标签 | 数量 |
|------|:----:|
`;
    const sortedTags = Object.entries(tagStats).sort((a, b) => b[1] - a[1]);
    for (const [tag, count] of sortedTags) {
        md += `| ${tag} | ${count} |\n`;
    }

    md += `
## 🚇 地铁时段统计

| 时段 | 新题 | 复习 |
|------|:----:|:----:|
`;
    for (const [slot, counts] of Object.entries(slotStats)) {
        md += `| ${slot} | ${counts.新题 || 0} | ${counts.复习 || 0} |\n`;
    }

    md += `
## 📆 本周打卡

| 指标 | 数值 |
|------|------|
| 有记录天数 | **${weekly.daysWithRecord}** / 7 |
| 周目标 (≥5天) | ${weekly.met ? '✅ 达标' : '❌ 未达标'} |

## 📋 每日打卡记录

详见 [checkin.md](checkin.md)（按日汇总：时段、新题/复习、题目明细）

## 📅 月度统计

| 月份 | 天数 | 总题数 | Easy | Medium | Hard |
|------|:---:|:------:|:----:|:------:|:----:|
`;
    const sortedMonths = Object.entries(monthly).sort((a, b) => a[0].localeCompare(b[0]));
    for (const [key, m] of sortedMonths) {
        md += `| ${key} | ${m.dayCount} | ${m.count} | ${m.difficulty['Easy'] || 0} | ${m.difficulty['Medium'] || 0} | ${m.difficulty['Hard'] || 0} |\n`;
    }

    md += `
## 🌡️ 热力图

${heatmapSVG}

---

## 📝 最近解题记录

| 日期 | 题目 |
|------|------|
`;
    const recent = [...solutions].sort((a, b) => b.date.localeCompare(a.date) || a.file.localeCompare(b.file)).slice(0, 20);
    for (const s of recent) {
        const name = s.file.replace('.java', '');
        const relPath = s.filePath.replace(/\\/g, '/').replace(/^.*?(?=src\/main\/java\/algo)/, '');
        md += `| ${s.date} | [${name}](${relPath}) |\n`;
    }

    md += `\n> 🚀 继续加油！\n`;
    return md;
}

function updateMainReadme(solutions) {
    const readmePath = join(import.meta.dirname, '..', 'README.md');
    let content = readFileSync(readmePath, 'utf-8');

    const sorted = [...solutions].sort((a, b) => b.date.localeCompare(a.date) || a.file.localeCompare(b.file));

    let table = `| 日期 | 题目 | 难度 | 标签 |\n|------|------|:----:|------|\n`;
    for (const s of sorted) {
        const tags = s.tags ? s.tags.join(', ') : '-';
        const diff = s.difficulty || '-';
        const name = s.file.replace('.java', '');
        const relPath = s.filePath.replace(/\\/g, '/').replace(/^.*?(?=src\/main\/java\/algo)/, '');
        table += `| ${s.date} | [${name}](${relPath}) | ${diff} | ${tags} |\n`;
    }

    const startMarker = '<!-- SOLUTIONS_START -->';
    const endMarker = '<!-- SOLUTIONS_END -->';
    const startIdx = content.indexOf(startMarker);
    const endIdx = content.indexOf(endMarker);

    if (startIdx === -1 || endIdx === -1) {
        console.warn('   ⚠️  Cannot find SOLUTIONS markers in README.md, skipping');
        return;
    }

    const before = content.slice(0, startIdx + startMarker.length);
    const after = content.slice(endIdx);
    content = `${before}\n\n${table}\n${after}`;
    writeFileSync(readmePath, content, 'utf-8');
    console.log(`   Saved ${readmePath}`);
}

// ─── main ──────────────────────────────────────────────────

function main() {
    console.log('🔍 Scanning solutions...');
    const solutions = collectSolutions();
    console.log(`   Found ${solutions.length} solutions`);

    console.log('📊 Generating statistics...');
    const calendar = buildCalendarMap(solutions);
    const monthly = buildMonthlyStats(solutions);
    const streak = computeStreak(calendar);

    // save calendar.json
    const calPath = join(STATS_DIR, 'calendar.json');
    writeFileSync(calPath, JSON.stringify(calendar, null, 2), 'utf-8');
    console.log(`   Saved ${calPath}`);

    // save daily checkin records
    const reviewByDate = collectReviewDone(loadReviewSchedule());
    const checkins = buildDailyCheckins(solutions, reviewByDate);
    const checkinJsonPath = join(STATS_DIR, 'checkin.json');
    writeFileSync(checkinJsonPath, JSON.stringify(checkins, null, 2), 'utf-8');
    console.log(`   Saved ${checkinJsonPath}`);
    const checkinMdPath = join(STATS_DIR, 'checkin.md');
    writeFileSync(checkinMdPath, generateCheckinMarkdown(checkins), 'utf-8');
    console.log(`   Saved ${checkinMdPath}`);

    // save stats README
    const md = generateMarkdown(solutions);
    const mdPath = join(STATS_DIR, 'README.md');
    writeFileSync(mdPath, md, 'utf-8');
    console.log(`   Saved ${mdPath}`);

    const progress = buildProgress(solutions);
    if (progress) {
        console.log('📝 Updating main README progress...');
        updateProgressInReadme(generateProgressMarkdown(progress));
    }

    // summary
    console.log('\n✅ Done!');
    console.log(`   Total: ${solutions.length} solutions`);
    console.log(`   Current streak: ${streak.current} days`);
    console.log(`   Monthly: ${JSON.stringify(Object.fromEntries(
        Object.entries(monthly).map(([k, v]) => [k, `${v.count} solutions / ${v.dayCount} days`])
    ))}`);
}

main();
