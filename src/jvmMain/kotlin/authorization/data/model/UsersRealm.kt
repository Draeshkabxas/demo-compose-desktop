package authorization.data.model

import authorization.domain.model.Jobs
import authorization.domain.model.Systems
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class UsersRealm:RealmObject{
    @PrimaryKey
    var id:ObjectId = ObjectId()
    var name: String =""
    var password:String=""
    var job:String =Jobs.None.name
    var systems:RealmList<String> = realmListOf()

    override fun toString(): String {
        return "UserRealm(id = $id, name = $name, password = $password, job = $job, systems = $systems)"
    }
}

