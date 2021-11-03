package tj.abduahad.check_contact.repository.base.impl

import android.content.Context
import tj.abduahad.check_contact.repository.base.ResourceRepository

/**
 * Created by ABDUAHAD FAIZULLOEV on 03,ноябрь,2021
 * abduahad.fayzulloev@gmail.com
 * http://abduahad.com/
 *
 */
class ResourceRepositoryImpl(val context: Context): ResourceRepository {
    override fun getString(id: Int): String {
        return context.getString(id)
    }
}