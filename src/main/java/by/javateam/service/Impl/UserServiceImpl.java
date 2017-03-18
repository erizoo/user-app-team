package by.javateam.service.Impl;


import by.javateam.StringSplit;
import by.javateam.dao.UserDao;
import by.javateam.model.User;
import by.javateam.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.JsonViewModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.monitorjbl.json.Match.match;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public List<User> getAll(int offset, int limit) {
        return userDao.loadAllWithOffsetAndLimit(offset, limit);
    }

    @Override
    public User getAllForId(int id) {
        return userDao.loadAllUsersForId(id);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public List<User> getNames() {
        return userDao.loadNames();
    }

    @Override
    public List<User> getAll() {
        return userDao.loadAll();
    }

    @Override
    public LocalDateTime getCreatedDate(int id) {
        return userDao.getCreatedDate(id);
    }

    @Override
    public String getAllWithParams(Integer offset, Integer limit, String exc, String inc) throws JsonProcessingException {
        StringSplit stringSplit = new StringSplit();
        List<User> userList = userDao.loadAllWithOffsetAndLimit(offset, limit);
        ObjectMapper mapper = new ObjectMapper().registerModule(new JsonViewModule());
        if (exc != null && inc == null) {
            String[] str = stringSplit.stringSplit(exc);
            return mapper.writeValueAsString(JsonView.with(userList)
                    .onClass(User.class, match()
                            .exclude("*")
                            .include(str)));
        }
        if (exc == null && inc != null) {
            String[] str = stringSplit.stringSplit(inc);
            return mapper.writeValueAsString(JsonView.with(userList)
                    .onClass(User.class, match()
                            .exclude(str)));
        }
        return mapper.writeValueAsString(JsonView.with(userList)
                .onClass(User.class, match().exclude("createdTimestamp", "modifiedTimestamp")));
    }

}
