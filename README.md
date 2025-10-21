# empty video gets generated for successful tests

This example project was generated with just 2 steps:

1) `pnpm create wdio@latest .` with "E2E Testing - of Web or Mobile Applications" option
2) `pnpm i -D @wdio/allure-reporter wdio-video-reporter` with corresponding reports configuration

## Steps to reproduce the issue

1) `pnpm i`
2) `pnpm run wdio`
3) `pnpm dlx allure-commandline generate out/allure-results --clean && pnpm dlx allure-commandline open allure-report`

You will find an empty .webm file created in HTML report.
