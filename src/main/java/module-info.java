module my.practice.monsters {
    requires com.almasb.fxgl.all;
    requires com.google.gson;

    exports my.practice.monsters;
    opens my.practice.monsters to com.almasb.fxgl.core;
}