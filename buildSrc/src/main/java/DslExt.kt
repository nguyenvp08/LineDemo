import org.gradle.api.artifacts.dsl.DependencyHandler


fun DependencyHandler.`annotationProcessor`(dependencyArray: Array<String>) =
        dependencyArray.forEach {
            add("annotationProcessor", it)
        }

fun DependencyHandler.`kapt`(dependencyArray: Array<String>) =
        dependencyArray.forEach {
            add("kapt", it)
        }

fun DependencyHandler.`implementation`(dependencyArray: Array<String>) =
        dependencyArray.forEach {
            add("implementation", it)
        }

fun DependencyHandler.`debugImplementation`(dependencyArray: Array<String>) =
        dependencyArray.forEach {
            add("debugImplementation", it)
        }

fun DependencyHandler.`releaseImplementation`(dependencyArray: Array<String>) =
        dependencyArray.forEach {
            add("releaseImplementation", it)
        }