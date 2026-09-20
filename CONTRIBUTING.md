# 贡献指南 · Contributing

感谢关注 **qualitest-demo**（质衡接口靶场）。

| 仓库 | 用途 |
|------|------|
| [`qualitest`](https://github.com/qualitest-hq/qualitest) | 主平台 + Web |
| [`qualitest-demo`](https://github.com/qualitest-hq/qualitest-demo)（本仓） | 接口靶场 |
| [`qualitest-intellij-plugin`](https://github.com/qualitest-hq/qualitest-intellij-plugin) | IDEA 插件 |

参与本社区即表示同意 [`CODE_OF_CONDUCT.md`](./CODE_OF_CONDUCT.md)。  
安全漏洞请走 [`SECURITY.md`](./SECURITY.md)，不要开公开 Issue。

## 欢迎什么

场景数据笔误、部署文档、test-support 接入体验、靶场业务缺口等，开 Issue 或 PR 均可。半成品想法先 Issue 聊聊也行。

## 开发摘要

- JDK 17+、Maven 3+、MySQL 8+、Redis；前端见 `demo-ui/`（Node / pnpm）  
- 一键：`scripts/quick-start`（见 README）；细节见 [`docs/deploy.md`](./docs/deploy.md)  
- 请勿提交 `.env`、密钥、构建产物

大改动建议先 Issue。维护者业余时间处理，**不承诺固定 SLA**。

本仓以 **[Apache License 2.0](./LICENSE)** 发布；贡献授权约定与主仓 [CONTRIBUTING](https://github.com/qualitest-hq/qualitest/blob/main/CONTRIBUTING.md) 相同。
