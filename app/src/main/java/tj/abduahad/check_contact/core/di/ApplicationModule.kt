package tj.abduahad.check_contact

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import tj.abduahad.check_contact.repository.base.ResourceRepository
import tj.abduahad.check_contact.repository.base.impl.ResourceRepositoryImpl
import tj.abduahad.check_contact.repository.network.client.APIService
import tj.abduahad.check_contact.repository.network.client.RetrofitInstance
import tj.abduahad.check_contact.repository.network.TokenRepository
import tj.abduahad.check_contact.repository.network.impl.TokenRepositoryImpl


object ApplicationModule {

    val applicationModule: Module = module {
        single<ResourceRepository> { ResourceRepositoryImpl(androidContext()) }

        single<TokenRepository> { TokenRepositoryImpl() }

        single<APIService> {
            RetrofitInstance(get()).getRetrofitInstance()!!.create(APIService::class.java)
        }
    }
}
