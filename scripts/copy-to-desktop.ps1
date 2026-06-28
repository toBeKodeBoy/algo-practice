$src = "D:\files\idea\algo-practice\review\weekly\2026-06-22-周计划算法"
$dst = [Environment]::GetFolderPath('Desktop')

Copy-Item "${src}.md" $dst -Force
Copy-Item "${src}.pdf" $dst -Force

Write-Host "Copied to desktop: $dst"
