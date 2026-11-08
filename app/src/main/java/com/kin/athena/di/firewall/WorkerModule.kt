package com.kin.athena.di.firewall

/*
 * Hilt WorkManager does not require a custom worker binding here.
 *
 * `@HiltWorker` + `HiltWorkerFactory` in `App` are enough for generated worker
 * creation. Keeping a custom `@AssistedFactory`/`@Binds` worker map here causes
 * KSP to reject the worker return type with:
 *
 *   Invalid return type: T. An assisted factory's abstract method must return a
 *   type with an @AssistedInject-annotated constructor.
 *
 * This file intentionally left empty to avoid generating invalid Hilt worker code.
 */
