package tomeko.hymod.config

//? if !forge {
import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import org.polyfrost.oneconfig.utils.v1.dsl.createScreen

class ModMenuIntegration : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return { _ -> HyModConfig.createScreen() }
    }
}
//?}