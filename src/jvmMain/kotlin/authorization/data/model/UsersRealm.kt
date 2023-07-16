package authorization.data.model

import authorization.domain.model.Jobs
import authorization.domain.model.Systems
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class UsersRealm:RealmObject{
    @PrimaryKey
    var id:ObjectId = ObjectId()
    var name: String =""
    var password:String=""
    var job:String =Jobs.Viewer.name
    var systems:String = Systems.Home.name

}

