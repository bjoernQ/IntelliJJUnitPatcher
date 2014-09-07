package de.mobilej.intellij;

import com.intellij.execution.JUnitPatcher;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.openapi.module.Module;
import com.intellij.util.PathsList;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple JUnitPatcher.
 *
 * Moves first four elements of the classpath to the end and adds "./build/test-classes/" to the classpath.
 *
 * Created by bjoern on 29.08.14.
 */
public class StudioJUnitPatcher extends JUnitPatcher {

    @Override
    public void patchJavaParameters(@Nullable Module module, JavaParameters javaParameters) {

        // MODIFY THE CLASSPATH HERE!
        PathsList classpath = javaParameters.getClassPath();
        List<String> pathList = classpath.getPathList();

        ArrayList<String> moveToEnd = new ArrayList<String>();
        moveToEnd.add( pathList.get(0) );
        moveToEnd.add( pathList.get(1) );
        moveToEnd.add( pathList.get(2) );
        moveToEnd.add( pathList.get(3) );

        for(String s : moveToEnd){
            classpath.remove(s);
        }

        for(String s : moveToEnd){
            classpath.add(s);
        }


        // add test classes

        // they are only built when "compileTestDebugJava" is executed
        // so add
        // "assembleDebug.dependsOn('compileTestDebugJava')" to your gradle.build to always have
        // the classes in sync with the test sources.
        classpath.add( new File( new File(module.getModuleFilePath()).getParent() ,"build/test-classes/") );

    }

}
