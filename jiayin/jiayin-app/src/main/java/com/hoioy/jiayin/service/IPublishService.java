package com.hoioy.jiayin.service;

import com.hoioy.jiayin.dto.MessageDTO;

public interface IPublishService {

    public MessageDTO saveDraft(MessageDTO dto);
    public MessageDTO publish(MessageDTO dto);
}
