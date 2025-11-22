import com.diacht.ktest.TaskL2
import com.diacht.ktest.TaskValidationResults
import com.diacht.ktest.domain.usecases.GenerateL2TaskUseCase
import com.diacht.ktest.domain.usecases.GenerateL3TaskUseCase
import com.diacht.ktest.domain.usecases.ValidateL2DoubleTaskUseCase
import com.diacht.ktest.domain.usecases.ValidateL2IntTaskUseCase
import com.diacht.ktest.domain.usecases.ValidateL2StrTaskUseCase
import com.diacht.ktest.domain.usecases.ValidateL2UseCase
import org.example.KTests.BuildConfig
import org.example.helloworld.seed
import org.junit.jupiter.api.Test
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class MainTest {
    @Test
    fun testGithubUser() {
        assertNotEquals(seed(), "user", "Впишіть ім'я СВОГО GitHub користувача.")
        assertTrue(seed().isNotEmpty(), "Не вказано ім'я GitHub користувача")
    }
}