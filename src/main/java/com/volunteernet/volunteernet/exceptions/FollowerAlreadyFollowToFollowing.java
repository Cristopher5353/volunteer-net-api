package com.volunteernet.volunteernet.exceptions;

public class FollowerAlreadyFollowToFollowing extends RuntimeException{
    public FollowerAlreadyFollowToFollowing() {
        super("El seguidor ya esta siguiendo a ese usuario");
    }   
}
