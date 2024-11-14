package com.techadive.sampledataapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.model.Statement

class CoroutineTestRule : TestRule {
    // The test dispatcher for running coroutines
    val testDispatcher = TestCoroutineDispatcher()

    override fun apply(base: Statement, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                // Set the Main dispatcher to the test dispatcher before the test runs
                Dispatchers.setMain(testDispatcher)

                try {
                    base.evaluate() // Run the test
                } finally {
                    // Reset the Main dispatcher after the test finishes
                    Dispatchers.resetMain()
                    testDispatcher.cleanupTestCoroutines()
                }
            }
        }
    }
}