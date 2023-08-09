package com.volunteernet.volunteernet.dto.message;

public class MessageResponseWebsocketDto {
    private MessageResponseDto messageResponseDto;
    private int chatId;

    public MessageResponseDto getMessageResponseDto() {
        return messageResponseDto;
    }

    public void setMessageResponseDto(MessageResponseDto messageResponseDto) {
        this.messageResponseDto = messageResponseDto;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
