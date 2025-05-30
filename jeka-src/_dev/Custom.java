package _dev;

import dev.jeka.core.tool.KBean;
import dev.jeka.core.tool.JkDep;
import dev.jeka.core.tool.JkPostInit;
import dev.jeka.core.tool.builtins.base.BaseKBean;

// Declare non-prod dependencies here (build, tests, compile-only deps)
@JkDep("org.junit.jupiter:junit-jupiter")
@JkDep("org.junit.platform:junit-platform-launcher")
@JkDep("org.junit:junit-bom:5.12.2@pom")
class Custom extends KBean {

    @JkPostInit(required = true)
    private void postInit(BaseKBean baseKBean) {
        // configure baseKBean here
    }

}