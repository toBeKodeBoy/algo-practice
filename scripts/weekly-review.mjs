import { readFileSync, writeFileSync, mkdirSync } from 'fs';
import { join } from 'path';
import { marked } from 'marked';
import { chromium } from 'playwright';

const ROOT = join(import.meta.dirname, '..');
const REVIEW_DIR = join(ROOT, 'review');
const SCHEDULE_PATH = join(REVIEW_DIR, 'schedule.json');
const HOT100_PATH = join(REVIEW_DIR, 'hot100.json');
const OUT_DIR = join(REVIEW_DIR, 'weekly');

function pad2(n) {
  return String(n).padStart(2, '0');
}

function isoDateLocal(d) {
  const y = d.getFullYear();
  const m = pad2(d.getMonth() + 1);
  const day = pad2(d.getDate());
  return `${y}-${m}-${day}`;
}

function addDays(dateStr, days) {
  const d = new Date(dateStr + 'T12:00:00');
  d.setDate(d.getDate() + days);
  return isoDateLocal(d);
}

function getMondayOfCurrentWeek() {
  const now = new Date();
  // Use local timezone, pin to noon to avoid DST edge cases.
  const d = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 12, 0, 0);
  const day = d.getDay(); // 0=Sun,1=Mon,...6=Sat
  const diffToMonday = (day + 6) % 7; // Mon->0, Sun->6
  d.setDate(d.getDate() - diffToMonday);
  return isoDateLocal(d);
}

function weekdayZh(dateStr) {
  const d = new Date(dateStr + 'T12:00:00');
  const map = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];
  return map[d.getDay()];
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

function loadJson(path) {
  return JSON.parse(readFileSync(path, 'utf-8'));
}

function lcIdFromNum(num) {
  return `LC_${String(num).padStart(4, '0')}`;
}

function pickNewProblems(hot100, schedule, count, { preferredPhaseId = 'P2' } = {}) {
  const scheduled = new Set(Object.keys(schedule));
  const picks = [];

  const phases = hot100.phases || [];
  let startIdx = phases.findIndex(p => p.id === preferredPhaseId);
  if (startIdx < 0) startIdx = 0;
  const ordered = [...phases.slice(startIdx), ...phases.slice(0, startIdx)];

  for (const phase of ordered) {
    const nums = phase.problems || [];
    for (const n of nums) {
      const lcId = lcIdFromNum(n);
      if (scheduled.has(lcId)) continue;
      picks.push({ lcId, title: hot100.titles?.[String(n)] || lcId, phase: phase.id });
      if (picks.length >= count) return picks;
    }
  }
  return picks;
}

function collectReviewItems(schedule) {
  const dueByDate = new Map();
  const allPending = [];

  for (const [lcId, entry] of Object.entries(schedule)) {
    for (const r of entry.reviews || []) {
      if (r.done) continue;
      const item = {
        lcId,
        title: entry.title || lcId,
        round: r.round,
        due: r.due,
      };
      allPending.push(item);
      if (!dueByDate.has(r.due)) dueByDate.set(r.due, []);
      dueByDate.get(r.due).push(item);
    }
  }

  for (const [d, items] of dueByDate.entries()) {
    items.sort((a, b) => a.lcId.localeCompare(b.lcId) || a.round.localeCompare(b.round));
  }

  allPending.sort((a, b) => a.due.localeCompare(b.due) || a.lcId.localeCompare(b.lcId) || a.round.localeCompare(b.round));
  return { dueByDate, allPending };
}

function buildWeekdays(mondayStr) {
  return [0, 1, 2, 3, 4].map(i => addDays(mondayStr, i));
}

function buildPlanMarkdown({ monday, friday, weekdays, newPicks, dueByDate, overduePool, maxReviewPerDay }) {
  const generatedAt = isoDateLocal(new Date());

  let md = `# 周学习/复习计划 ${monday} ~ ${friday}\n\n`;
  md += `> 自动生成于 ${generatedAt} | 数据源：\`${SCHEDULE_PATH}\` + \`${HOT100_PATH}\`\n\n---\n\n`;

  for (let i = 0; i < weekdays.length; i++) {
    const date = weekdays[i];
    const w = weekdayZh(date);
    const newItem = newPicks[i] || null;
    const due = dueByDate.get(date) || [];

    const reviews = [];
    reviews.push(...due);

    // Fill with overdue items up to maxReviewPerDay (overdue only, due items always included).
    while (reviews.length < maxReviewPerDay && overduePool.length > 0) {
      reviews.push(overduePool.shift());
    }

    md += `## ${date} ${w}\n\n`;
    md += `### 新题（学习）\n\n`;
    if (newItem) {
      md += `- ${newItem.lcId} ${newItem.title}（${newItem.phase}）\n\n`;
    } else {
      md += `- （本周新题候选不足）\n\n`;
    }

    md += `### 复习（到期 + 逾期分配）\n\n`;
    if (reviews.length === 0) {
      md += `- 无\n\n`;
    } else {
      md += `| 题号 | 题目 | 轮次 | 应复习日 |\n|------|------|:----:|----------|\n`;
      for (const r of reviews) {
        md += `| ${r.lcId} | ${r.title} | ${r.round} | ${r.due} |\n`;
      }
      md += `\n`;
    }

    md += `---\n\n`;
  }

  return md;
}

function markdownToHtml(md, { title }) {
  const body = marked.parse(md);
  const css = `
    @page { size: A4; margin: 18mm 16mm; }
    body { font-family: "Microsoft YaHei", "Noto Sans CJK SC", "PingFang SC", Arial, sans-serif; color: #111; }
    h1 { font-size: 20pt; margin: 0 0 10pt; }
    h2 { font-size: 14pt; margin: 18pt 0 8pt; }
    h3 { font-size: 12pt; margin: 12pt 0 6pt; }
    p, li, table { font-size: 10.5pt; }
    blockquote { margin: 10pt 0; padding: 6pt 10pt; border-left: 3pt solid #ddd; color: #555; }
    table { width: 100%; border-collapse: collapse; margin: 8pt 0 0; }
    th, td { border: 1px solid #ddd; padding: 6pt 8pt; vertical-align: top; }
    th { background: #f5f5f5; }
    code { font-family: Consolas, "Cascadia Mono", Menlo, monospace; font-size: 9.5pt; }
    hr { border: none; border-top: 1px solid #eee; margin: 14pt 0; }
  `;

  return `<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>${title}</title>
  <style>${css}</style>
</head>
<body>
${body}
</body>
</html>`;
}

async function renderPdf(html, outPdfPath) {
  const browser = await chromium.launch();
  try {
    const page = await browser.newPage();
    await page.setContent(html, { waitUntil: 'networkidle' });
    await page.pdf({
      path: outPdfPath,
      format: 'A4',
      printBackground: true,
    });
  } finally {
    await browser.close();
  }
}

async function main() {
  const args = parseArgs(process.argv.slice(2));
  const monday = args.monday || getMondayOfCurrentWeek();
  const weekdays = buildWeekdays(monday);
  const friday = weekdays[4];
  const maxReviewPerDay = Number(args.maxReview || 6);
  const phase = typeof args.phase === 'string' ? args.phase : 'P2';

  const schedule = loadJson(SCHEDULE_PATH);
  const hot100 = loadJson(HOT100_PATH);

  const newPicks = pickNewProblems(hot100, schedule, 5, { preferredPhaseId: phase });
  const { dueByDate, allPending } = collectReviewItems(schedule);

  const overduePool = allPending.filter(x => x.due < monday);

  const md = buildPlanMarkdown({
    monday,
    friday,
    weekdays,
    newPicks,
    dueByDate,
    overduePool: [...overduePool],
    maxReviewPerDay,
  });

  mkdirSync(OUT_DIR, { recursive: true });
  const outBase = `weekly-${monday}`;
  const outMdPath = join(OUT_DIR, `${outBase}.md`);
  const outPdfPath = join(OUT_DIR, `${outBase}.pdf`);

  writeFileSync(outMdPath, md, 'utf-8');

  const html = markdownToHtml(md, { title: `周学习/复习计划 ${monday} ~ ${friday}` });
  await renderPdf(html, outPdfPath);

  console.log(`✅ Saved ${outMdPath}`);
  console.log(`✅ Saved ${outPdfPath}`);
}

main().catch((err) => {
  console.error(err);
  process.exit(1);
});

