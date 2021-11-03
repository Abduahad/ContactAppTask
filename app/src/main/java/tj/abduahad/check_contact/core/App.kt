package tj.abduahad.check_contact.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tj.abduahad.check_contact.ApplicationModule


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(applicationContext)
            modules(ApplicationModule.applicationModule)
        }
    }
}