package com.kssidll.workin

import androidx.test.ext.junit.runners.*
import androidx.test.platform.app.*
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.workin", appContext.packageName)
    }
}