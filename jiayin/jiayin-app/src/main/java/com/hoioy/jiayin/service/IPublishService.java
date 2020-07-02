package com.hoioy.jiayin.service;

import com.hoioy.jiayin.dto.MessageDTO;

public interface IPublishService {

    MessageDTO saveDraft(MessageDTO dto);

    MessageDTO publish(MessageDTO dto);

    MessageDTO rePublish(MessageDTO dto);
}
