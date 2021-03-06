package io.github.classgraph.features;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Test;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

/**
 * The Class AnnotationEquality.
 */
public class MethodParameterAnnotations {
    /**
     * The Annotation W.
     */
    @Retention(RetentionPolicy.RUNTIME)
    private static @interface W {
    }

    /**
     * The Annotation X.
     */
    @Retention(RetentionPolicy.RUNTIME)
    private static @interface X {
    }

    /**
     * The Class Y.
     */
    private abstract static class Y {
        abstract void w(@W int w);
    }

    /**
     * The Class Z.
     */
    private abstract static class Z {
        abstract void x(@X int x);
    }

    /**
     * Test equality of JRE-instantiated Annotation with proxy instance instantiated by ClassGraph.
     */
    @Test
    public void annotationEquality() {
        try (ScanResult scanResult = new ClassGraph()
                .whitelistPackages(MethodParameterAnnotations.class.getPackage().getName()).enableAllInfo()
                .scan()) {
            assertThat(scanResult.getClassInfo(Y.class.getName()).getMethodParameterAnnotations().getNames())
                    .containsOnly(W.class.getName());
            assertThat(scanResult.getClassInfo(Z.class.getName()).getMethodParameterAnnotations().getNames())
                    .containsOnly(X.class.getName());
            assertThat(scanResult.getClassesWithMethodParameterAnnotation(W.class.getName()).getNames())
                    .containsOnly(Y.class.getName());
            assertThat(scanResult.getClassesWithMethodParameterAnnotation(X.class.getName()).getNames())
                    .containsOnly(Z.class.getName());
        }
    }
}
