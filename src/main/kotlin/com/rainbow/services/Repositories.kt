import com.rainbow.services.User
import org.springframework.data.mongodb.core.*
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.isEqualTo

class UserRepository(private val mongo: ReactiveFluentMongoOperations) {

    suspend fun count() = mongo.query<User>().awaitCount()

    fun findAll() = mongo.query<User>().flow()

    suspend fun findOne(id: String) = mongo.query<User>().matching(query(where("id").isEqualTo(id))).awaitOne()

    suspend fun insert(user: User) = mongo.insert<User>().oneAndAwait(user)

    suspend fun update(user: User) = mongo.update<User>().replaceWith(user).asType<User>().findReplaceAndAwait()!!

}