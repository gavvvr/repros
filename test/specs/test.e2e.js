import { expect, browser, $ } from '@wdio/globals'

describe('My Login application', () => {
    it('should print web page title', async () => {
        await browser.url(`https://the-internet.herokuapp.com/login`)
        
        const title = await browser.getTitle()
        console.log('Hello ' + title + ' website');
    })
})

