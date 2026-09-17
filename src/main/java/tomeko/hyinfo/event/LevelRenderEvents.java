package tomeko.hyinfo.event;

//? if ornithe {
/*public final class LevelRenderEvents {
    private LevelRenderEvents() {}

    public static final Event<Start> START = Event.create(Start.class, listeners -> ctx -> {
        for (Start l : listeners) l.onStart(ctx);
    });

    public static final Event<AfterSetup> AFTER_SETUP = Event.create(AfterSetup.class, listeners -> ctx -> {
        for (AfterSetup l : listeners) l.onAfterSetup(ctx);
    });

    public static final Event<BeforeEntities> BEFORE_ENTITIES = Event.create(BeforeEntities.class, listeners -> ctx -> {
        for (BeforeEntities l : listeners) l.onBeforeEntities(ctx);
    });

    public static final Event<AfterEntities> AFTER_ENTITIES = Event.create(AfterEntities.class, listeners -> ctx -> {
        for (AfterEntities l : listeners) l.onAfterEntities(ctx);
    });

    public static final Event<AfterTranslucent> AFTER_TRANSLUCENT_FEATURES = Event.create(AfterTranslucent.class, listeners -> ctx -> {
        for (AfterTranslucent l : listeners) l.onAfterTranslucent(ctx);
    });

    public static final Event<Last> LAST = Event.create(Last.class, listeners -> ctx -> {
        for (Last l : listeners) l.onLast(ctx);
    });

    public static final Event<End> END = Event.create(End.class, listeners -> ctx -> {
        for (End l : listeners) l.onEnd(ctx);
    });

    @FunctionalInterface
    public interface Start {
        void onStart(RenderWorldLastEvent context);
    }

    @FunctionalInterface
    public interface AfterSetup {
        void onAfterSetup(RenderWorldLastEvent context);
    }

    @FunctionalInterface
    public interface BeforeEntities {
        void onBeforeEntities(RenderWorldLastEvent context);
    }

    @FunctionalInterface
    public interface AfterEntities {
        void onAfterEntities(RenderWorldLastEvent context);
    }

    @FunctionalInterface
    public interface AfterTranslucent {
        void onAfterTranslucent(RenderWorldLastEvent context);
    }

    @FunctionalInterface
    public interface Last {
        void onLast(RenderWorldLastEvent context);
    }

    @FunctionalInterface
    public interface End {
        void onEnd(RenderWorldLastEvent context);
    }
}
*///?}