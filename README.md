# electron-service:bridge: Failed to connect after 4 attempts

This example project was generated with just 2 commands:

1) `pnpm create wdio@latest .`
2) `pnpm create create-electron-app@latest .`

The error gets reproduced since `wdio-electron-service@8.1.1`

## Steps to reproduce the issue

1) `pnpm i`
2) `pnpm run package`
3) `pnpm run wdio`

You will get an error:

```log
[0-0] 2025-10-05T21:39:59.513Z WARN electron-service:bridge: Connection attempt 1 failed: Request Error:
[0-0] 2025-10-05T21:40:09.699Z WARN electron-service:bridge: Connection attempt 2 failed: Request Error:
[0-0] 2025-10-05T21:40:19.886Z WARN electron-service:bridge: Connection attempt 3 failed: Request Error:
[0-0] 2025-10-05T21:40:30.033Z WARN electron-service:bridge: Connection attempt 4 failed: Request Error:
[0-0] 2025-10-05T21:40:30.034Z ERROR electron-service:bridge: Failed to connect after 4 attempts
[0-0] 2025-10-05T21:40:30.039Z ERROR @wdio/utils:shim: Error: Request Error:
[0-0]     at ClientRequest.<anonymous> (/Users/user/Documents/projects/repros/node_modules/@wdio/src/devTool.ts:128:20)
[0-0]     at ClientRequest.emit (node:events:524:28)
[0-0]     at ClientRequest.emit (node:domain:489:12)
[0-0]     at emitErrorEvent (node:_http_client:104:11)
[0-0]     at Socket.socketErrorListener (node:_http_client:512:5)
[0-0]     at Socket.emit (node:events:524:28)
[0-0]     at Socket.emit (node:domain:489:12)
[0-0]     at emitErrorNT (node:internal/streams/destroy:170:8)
[0-0]     at emitErrorCloseNT (node:internal/streams/destroy:129:3)
[0-0]     at process.processTicksAndRejections (node:internal/process/task_queues:90:21)
```

## How to fix

### Option 1

- downgrade WDIO Electron service:

```shell
pnpm install -D wdio-electron-service@8.1.0`
```

- run tests again (`pnpm run wdio`) and the problem will disappear.

- upgrade to v8.1.1 (`pnpm install -D wdio-electron-service@8.1.1`) and
it will appear again

### Option 2

Open [forge.config.js](forge.config.js) and enable `EnableNodeCliInspectArguments`:

```diff
      [FuseV1Options.EnableNodeOptionsEnvironmentVariable]: false,
-      [FuseV1Options.EnableNodeCliInspectArguments]: false,
+.     [FuseV1Options.EnableNodeCliInspectArguments]: true,
      [FuseV1Options.EnableEmbeddedAsarIntegrityValidation]: true,
```

Run tests again (`pnpm run wdio`) and the problem will disappear.
