import { Plugin } from 'obsidian'

export default class MyPlugin extends Plugin {
  override onload() {
    console.log('Overridden!')
  }

  static greeting(name: string): string {
    return `Hello, ${name}!`
  }
}
