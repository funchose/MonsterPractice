module my.practice.monsters {
    requires com.almasb.fxgl.all;

    exports my.practice.monsters;
    opens my.practice.monsters to com.almasb.fxgl.core;
}