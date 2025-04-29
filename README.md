
# Koin <img src="https://insert-koin.io/img/koin_new_logo.png" alt="Koin Logo" width="35" height="35"> Dependency Injection - Android

#### 🔗 Connect & Follow Me

<span style="display: inline-block; margin-right: 10px;">
  <a href="https://www.linkedin.com/in/fahadammar" target="_blank" style="text-decoration:none;">
    <img src="https://img.shields.io/badge/LinkedIn-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn Badge"/>
  </a>
</span>
<span style="display: inline-block;">
  <a href="https://github.com/fahadammar" target="_blank" style="text-decoration:none;">
    <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub Badge"/>
  </a>
</span>








This README is based on deep exploration and real use-cases, updated to reflect best practices, common pitfalls, and correct ways to work with Koin (especially with latest versions).

## What is Koin?

Koin is a lightweight dependency injection framework for developers, designed to be pragmatic, simple, and powerful. It works without requiring annotation processing or heavy code generation.

## Setting up Koin

Add Koin dependencies to your **build.gradle**:
```
implementation(project.dependencies.platform("io.insert-koin:koin-bom:4.0.4"))
implementation(libs.koin.core)
implementation(libs.koin.android)
```

Add in your **libs.versions.toml**:
```
koin-android = { module = "io.insert-koin:koin-android" }
koin-core = { module = "io.insert-koin:koin-core" }
```

## Understanding Core Koin Concepts

* **Module:** A container where you define how your dependencies are provided.

* **Single:** A singleton instance - only one instance is created.

* **Factory:** A new instance is created every time you ask for it.

* **Scope:** A dependency that lives as long as a particular lifecycle (like an Activity or Fragment).

* **KoinComponent:** A helper interface to access get(), inject(), etc.

* **AndroidScopeComponent:** Provides automatic scoping with Activities and Fragments.

## Basic Definitions

**Define your modules**
```
val appModule = module {
    single { UserInfo() }
    single { Pilot() }
    single { Wings() }
    single { Airplane(get(), get()) }
}
```

## Start Koin in your Application class
```
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}
```

## Injecting dependencies

**Normal Injection** 
```
class MainActivity : AppCompatActivity() {
    private val sessionManager: SessionManager by inject()
}
```
## Injecting Scoped Dependencies in Activity
```
class MainActivity : AppCompatActivity(), AndroidScopeComponent {
    override val scope: Scope by activityScope()
    private val sessionManager: SessionManager by inject()
}
```

## Injecting Scoped Dependencies in Fragment
```
class JustAFragment : Fragment(), AndroidScopeComponent {
    override val scope: Scope by fragmentScope()
    private val printInFragment: PrintInFragment by inject()
}
```

## Handling Custom Scopes

**Sometimes you need dynamic scopes.
Example:**
```
class MainActivity : AppCompatActivity(), AndroidScopeComponent {
    override val scope: Scope by activityScope()

    private val dynamicScope: Scope by lazy { getKoin().createScope(getServerName(), named(getServerName())) }

    private val server: Server by lazy { dynamicScope.get() }
}
```

## Manually Closing Scopes

If you manually create scopes using `createScope()`, you **must close them explicitly** to prevent memory leaks.

### Example: Manual Scope Closure in Activity

```
class MainActivity : AppCompatActivity(), KoinScopeComponent {

    // Custom scope with dynamic name
    override val scope: Scope by lazy {
        getKoin().createScope("CustomActivityScope", named("CustomActivityScope"))
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close() // Close the manually created scope
    }
}
```

### Example: Manual Scope Closure in Fragment

```
class SampleFragment : Fragment(), KoinScopeComponent {

    override val scope: Scope by lazy {
        getKoin().createScope("CustomFragmentScope", named("CustomFragmentScope"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.close() // Close the manually created scope
    }
}
```

### Important Notes:

- Only call `scope.close()` for **manually created scopes**.
- If you're using `activityScope()` or `fragmentScope()` via `AndroidScopeComponent`, **you don't need to close them manually** Koin manages those automatically.
- Always match the **scope lifecycle** to the appropriate component (`onDestroy` for `Activity`, `onDestroyView` for `Fragment`) to avoid premature destruction or leaks.

## Defining Scoped Dependencies
```
val scopedModule = module {
    scope<MainActivity> {
        scoped { SessionManager() }
    }
}
```

This ties **SessionManager's lifecycle to the MainActivity.**

## Dynamic Scope Creation

Creating a custom scope dynamically:
```
class MainActivity : AppCompatActivity(), KoinScopeComponent {
    override val scope: Scope by activityScope()

    val dynamicScope: Scope by lazy { getKoin().createScope("MyDynamicScope", named("MyDynamicScope")) }

    private val server: Server by lazy { dynamicScope.get() }
}
```
Useful for cases like UserSession, Dynamic Feature Scopes, etc

## Real-World Scoped Use Case (Debug/Release Server)

When you need scoped instances for debug and production separately:
```
val serverModule = module {
    scope(named("DebugServerScope")) {
        scoped(named("DebugServer")) { Server("Debug Server") }
    }
    scope(named("ProdServerScope")) {
        scoped(named("ProdServer")) { Server("Production Server") }
    }
}
```
In the activity:
```
class MainActivity : AppCompatActivity(), AndroidScopeComponent {
    override val scope: Scope by activityScope()

    private val debugServer: Server by lazy {
        getKoin().createScope("DebugServerScope", named("DebugServerScope")).get(named("DebugServer"))
    }

    private val prodServer: Server by lazy {
        getKoin().createScope("ProdServerScope", named("ProdServerScope")).get(named("ProdServer"))
    }
}
```


## Primitive types injection

If your class constructor needs primitives like String, Int, etc., you can pass them using parametersOf().

**Example:**
```
class Engine(val model: String, val horsePower: Int)

val engineModule = module {
    factory { (model: String, horsePower: Int) -> Engine(model, horsePower) }
}

// Injecting
private val engine: Engine = get(parameters = { parametersOf("V8", 500) })
```
> [!WARNING]
> If the order of parameters is wrong, your app may crash.

## Using Qualifiers

**When to use Qualifiers:**

You have multiple definitions of the same type.

You need to distinguish between them.

**Example:**
```
val serverModule = module {
    single(named("DebugServer")) { Server("https://debug.server.com") }
    single(named("ReleaseServer")) { Server("https://release.server.com") }
}

// Inject
private val debugServer: Server by inject(named("DebugServer"))
private val releaseServer: Server by inject(named("ReleaseServer"))
```
## Lazy vs Eager Injection

**Lazy:**
```
private val sessionManager: SessionManager by lazy { scope.get() }
```

Creates instance only when used.

**Eager:**
```
private val sessionManager: SessionManager by inject()
```
Creates instance as soon as the component is created.

Use **lazy** if initialization is expensive or optional.

## Why create a centralized Component class?

To avoid repeating by **inject()** everywhere manually.

To organize injection points in one place.

Best if you need manual fine-grained control.

**Example:**
```
class Component : KoinComponent {
    val car: Car by inject()
}
```
## Common Errors & Solutions

If you encounter **Unresolved reference 'get'**, it usually means you forgot to implement **KoinComponent** or **KoinScopeComponent**; fix it by properly inheriting your class or using by **inject()**. If you see **NoDefinitionFoundException**, it's often because your module is missing or you provided a wrong qualifier; double-check your module setup and spelling. Finally, if you face **InstantiationException: no zero argument constructor**, it happens when you mistakenly inject dependencies into the constructor of an **Activity**; remember that Activities must have a no-argument constructor always inject dependencies as properties instead.

## Best Practices

* Use **named()** qualifier to differentiate same type instances.

* Use **AndroidScopeComponent** for easy, lifecycle-aware scoping.

* **Lazy inject** if you don't immediately need the object.

* Dynamic Scopes for user sessions, per-feature modules.

* **Centralized Component Class:** Avoid creating random KoinComponent classes everywhere. Create if absolutely necessary.

* **Clear Scopes** when no longer needed manually if you use custom KoinScopeComponent.

## Notes

* Koin handles the lifecycle of scoped objects automatically when you use AndroidScopeComponent.

* If you manually create scopes with **createScope()**, you need to close them manually.

* Prefer using **AndroidScopeComponent** unless you really need manual control.

* Koin is flexible, simple and very readable for dependency injection.

* Focus on proper scoping and qualifiers.

* Always think about who owns the object (Activity, Fragment, Global).

* Avoid manual memory leaks by binding lifecycles properly.

* Always define your modules cleanly.

* Prefer AndroidScopeComponent for automatic scope handling.

* Use parametersOf() carefully when passing primitives.

* Be careful with manual scopes; manage their lifecycle properly

Make sure to always check official [Koin Documentation](https://insert-koin.io/docs/setup/koin) for latest updates.
