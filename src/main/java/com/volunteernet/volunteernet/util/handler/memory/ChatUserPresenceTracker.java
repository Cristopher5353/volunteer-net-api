package com.volunteernet.volunteernet.util.handler.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.volunteernet.volunteernet.models.User;

@Component
public class ChatUserPresenceTracker {
    private final Map<Integer, List<User>> chatUsers = new ConcurrentHashMap<>();

    public void userConnectedToChat(Integer chatId, User user) {
        List<User> connectedUsers = chatUsers.getOrDefault(chatId, new ArrayList<User>());
        boolean isUserInChat = connectedUsers.stream().anyMatch(userConnected -> userConnected.getId() == user.getId());

        if(!isUserInChat) {
            connectedUsers.add(user);
            chatUsers.put(chatId, connectedUsers);
        }
    }

    public void userDisconnectedFromChat(Integer chatId, User user) {
        List<User> connectedUsers = chatUsers.get(chatId);

        if(connectedUsers != null) {
            chatUsers.put(chatId, connectedUsers.stream().filter(userFilter -> userFilter.getId() != user.getId()).collect(Collectors.toList()));
        }
    }

    public boolean isUserConnectedToChat(Integer chatId, User user) {
        List<User> connectedUsers = chatUsers.get(chatId);

        if(connectedUsers == null) {
            return false;
        }

        return connectedUsers.stream().anyMatch(userFilter -> userFilter.getId() == user.getId());
    }
}