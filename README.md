# DEPRICATED AS OF Android Studio 1.1-beta3 / Android Gradle Plugin 1.1.0-rc1 - WON'T WORK ANYMORE AND YOU SHOULD USE THE NOW BUILT IN FUNCTIONALITY

# JUnitPatcher for Android Studio

This is a very simple plugin which should enable the execution of JUnit tests in the JVM from inside Android Studio.

It plays together with the Robolectric Gradle Plugin.

After installation into Android Studio (tested with 0.8.7) it will patch the classpath of generated JUnit run configurations.

There is just one thing you should change in your build.gradle file.Add

```
// always compile tests when assembling the app
afterEvaluate { project ->
    String variantTestCompileTaskName = 'compileTestDebugJava';
    boolean found = false;

    project.tasks.each() { task ->
        String taskName = task.name;
        if(taskName.startsWith("compileTest") && taskName.endsWith("Java")){
            if(!found){
                variantTestCompileTaskName = taskName;
            }
            found = true;
        }
    };

    project.tasks.each() { task ->
        String taskName = task.name;
        if(taskName.startsWith("assemble")){
            String variantName = taskName.substring(8);
            task.dependsOn(variantTestCompileTaskName);
        }
    };

}
```


to your build file. This way Studio will compile the tests whenever the debug configuration is compiled.
Otherwise it's possible you will run old versions of your tests in Studio and changes to the tests are not taken into account when running the tests.

