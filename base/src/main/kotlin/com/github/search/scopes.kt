package com.github.search

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class RoomScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerApplication
