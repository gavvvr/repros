### Reproduction steps

0. Use modern Node.js v24-v26+

1. Try running

```shell
pnpm i && pnpm exec vitest
```

You will get an error about missing native binding for rolldown:

<details>
<summary>Click to expand logs</summary>

```
⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯ Startup Error ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯
Error: Cannot find native binding. npm has a bug related to optional dependencies (https://github.com/npm/cli/issues/4828). Please try `npm i` again after removing both package-lock.json and node_modules directory.
    at file:///Users/user/Documents/code/projects/repros/node_modules/.pnpm/rolldown@1.2.9/node_modules/rolldown/dist/shared/binding-BbrDfv1x.mjs:602:34
    at file:///Users/user/Documents/code/projects/repros/node_modules/.pnpm/rolldown@1.2.9/node_modules/rolldown/dist/shared/binding-BbrDfv1x.mjs:9:49
    ... 3 lines matching cause stack trace ...
    at async start (file:///Users/user/Documents/code/projects/repros/node_modules/.pnpm/vitest@4.1.11_vite@8.3.0/node_modules/vitest/dist/chunks/cac.uFydS1Z4.js:2339:27) {
  cause: Error: Cannot find module '@rolldown/binding-wasm32-wasi'
  Require stack:
  - /Users/user/Documents/code/projects/repros/node_modules/.pnpm/rolldown@1.2.9/node_modules/rolldown/dist/shared/binding-BbrDfv1x.mjs
      at file:///Users/user/Documents/code/projects/repros/node_modules/.pnpm/rolldown@1.2.9/node_modules/rolldown/dist/shared/binding-BbrDfv1x.mjs:497:18
      ... 7 lines matching cause stack trace ...
      at async start (file:///Users/user/Documents/code/projects/repros/node_modules/.pnpm/vitest@4.1.11_vite@8.3.0/node_modules/vitest/dist/chunks/cac.uFydS1Z4.js:2339:27) {
    cause: Error: Cannot find module './rolldown-binding.wasi.cjs'
    Require stack:
    - /Users/user/Documents/code/projects/repros/node_modules/.pnpm/rolldown@1.2.9/node_modules/rolldown/dist/shared/binding-BbrDfv1x.mjs
        at file:///Users/user/Documents/code/projects/repros/node_modules/.pnpm/rolldown@1.2.9/node_modules/rolldown/dist/shared/binding-BbrDfv1x.mjs:497:18
        ... 7 lines matching cause stack trace ...
        at async start (file:///Users/user/Documents/code/projects/repros/node_modules/.pnpm/vitest@4.1.11_vite@8.3.0/node_modules/vitest/dist/chunks/cac.uFydS1Z4.js:2339:27) {
      cause: [Error]
    }
  }
}
```

</details>

That's because the optional `@rolldown/binding-darwin-arm64@1.2.9` doesn't get resolved.
And the reason why `pnpm` decided to skip it is because the `package.json` of that rolldown binding declares:

```
"engines": {
  "node": "^20.19.0 || >=22.12.0"
},
```

(please note that the version of Node.js used for this experiment definitely fits this restriction)

As soon as you change the lower bound of runtime in package.json to be at least `>=22.12.0` and try:

```bash
rm -rf node_modules && pnpm i && pnpm exec vitest
```

the problem will disappear because pnpm now will resolve `@rolldown/binding-darwin-arm64` as expected.

So, pnpm is using the lower bound of `.devEngines.runtime.version` as if it's the version of Node.js
currently used for development (even though the version of Node.js used here is v24.x+)

### Describe the Bug

When lower bound of `.devEngines.runtime.version` in `package.json` does not match the `.engines.node`
of some optional transitive dependency, pnpm will skip installation of such optional dependency.

### Expected Behavior

`@rolldown/binding-darwin-arm64@1.2.9` is expected to be installed into `node_modules`
because my current Node.js runtime is v24 LTS.

If you check how npm will behave in this situation (`rm -rf node_modules && npm i && npm exec vitest`),
npm will resolve optional `@rolldown/binding-darwin-arm64@1.2.9` as expected if the current runtime supports it.
