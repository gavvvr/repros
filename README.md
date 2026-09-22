# Node.js v26 Chromedriver download problem

Use Node.js v26.

Try `pnpm install` and then `pnpm exec wdio`.

If you do it with Node.js v26, download will fail with the following message in logs and exit code 0:

```text
PROGRESS webdriver: Downloading Chromedriver 51.56%

Ended WebDriver sessions gracefully after a SIGINT signal was received!
```

Switching back to Node.js v26 fixes the problem
