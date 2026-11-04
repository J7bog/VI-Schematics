package vischematics;

import arc.Events;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.mod.Mod;

public class VISchematics extends Mod {
    
    static {
        Events.on(ClientLoadEvent.class, VISchematics::clientLoaded);
    }
    
    private static void clientLoaded(ClientLoadEvent event) {
    
    }
}
