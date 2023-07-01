package authorization.data.model

import authorization.domain.model.Jobs
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class UserRealm:RealmObject{
    @PrimaryKey
    var id:ObjectId = ObjectId()
    var name: String =""
    var password:String=""
    var job:String =Jobs.Viewer.name
}

