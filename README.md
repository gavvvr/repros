# wdio-electron-service bug

https://github.com/webdriverio-community/wdio-electron-service/issues/818

## `switchFrame` doesn't accept not-`await`ed element

Based on [`switchFrame()`](https://webdriver.io/docs/api/browser/switchFrame/) WDIO docs,
the following should work:

```javascript
await browser.switchFrame($('iframe'))
```

It's also proven by [e2e test](https://github.com/webdriverio/webdriverio/blob/v9.2.10/e2e/wdio/headless/test.e2e.ts#L517-L526) in main WDIO repository:

```javascript
it('should reset the frame when the page is reloaded', async () => {
    await browser.url('https://the-internet.herokuapp.com/iframe')
    await expect($('#tinymce')).not.toBePresent()
    await browser.switchFrame($('iframe'))
    await expect($('#tinymce')).toBePresent()
    await browser.refresh()
    await expect($('#tinymce')).not.toBePresent()
    await browser.switchFrame($('iframe'))
    await expect($('#tinymce')).toBePresent()
})
```

However `await browser.switchFrame($('iframe'))` construct
won't work with `wdio-electron-service` unless `$('iframe')` is `await`ed.

This repo demonstrates the problem.

## Steps to reproduce

0. `npm i` to resolve dependencies
1. `npm run package` to build an electron app displaying iframe from https://the-internet.herokuapp.com/iframe
2. `npm run wdio` to execute e2e test. It will fail if `switchFrame()` argument is not `await`ed
