package eu.lpinto.universe.api.dts;

import eu.lpinto.universe.api.util.Digest;
import eu.lpinto.universe.persistence.entities.User;

/**
 * User DTO - Data Transformation Object
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public class UserDTS extends BaseDTS<User, eu.lpinto.universe.api.dto.User> {

    public static final UserDTS T = new UserDTS();

    @Override
    public eu.lpinto.universe.api.dto.User toAPI(User entity) {
        return new eu.lpinto.universe.api.dto.User(
                entity.getEmail(),
                null, // passowrd is never released
                PersonDTS.id(entity.getPerson()),
                null, // tokens
                entity.getId(), entity.getName(), entity.getCreated(), entity.getUpdated());
    }

    @Override
    public User toDomain(Long id) {
        if (id == null) {
            return null;
        }

        return new User(id);
    }

    public User toDomain(final String email, final String password) {
        return new User(email, password);
    }

    public User toDomain(final String email, final String password, final String name) {
        return new User(email, password, name);
    }

    @Override
    public User toDomain(eu.lpinto.universe.api.dto.User dto) {
        return new User(
                dto.getEmail().toLowerCase(), Digest.getSHA(dto.getPassword()), PersonDTS.T.toDomain(dto.getPerson()), TokenDTS.T.toDomainIDs(dto.getTokens()),
                dto.getId(), dto.getName(), dto.getCreated(), dto.getUpdated());
    }
}
