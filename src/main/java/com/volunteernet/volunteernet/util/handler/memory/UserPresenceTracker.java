package com.volunteernet.volunteernet.util.handler.memory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import com.volunteernet.volunteernet.models.User;

@Component
public class UserPresenceTracker {
    private final Map<Integer, User> onlineUsers = new ConcurrentHashMap<>();

    public void userConnected(User user) {
        if(!isUserOnline(user.getId())) {
            onlineUsers.put(user.getId(), user);
        }
    }

    public void userDisconnected(Integer userId) {
        onlineUsers.remove(userId);
    }

    public boolean isUserOnline(Integer userId) {
        return onlineUsers.containsKey(userId);
    }

    public Collection<User> getOnlineUsers() {
        return onlineUsers.values();
    }
}
