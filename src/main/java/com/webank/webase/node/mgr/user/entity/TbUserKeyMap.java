/**
 * Copyright 2014-2019  the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webank.webase.node.mgr.user.entity;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * Entity class of table tb_user_key_mapping.
 */
@Data
public class TbUserKeyMap {

    private Integer mapId;
    private Integer userId;
    private Integer groupId;
    private String privateKey;
    private int mapStatus;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;

    public TbUserKeyMap() {
        super();
    }

    /**
     * init TbUserKeyMap.
     */
    public TbUserKeyMap(Integer userId,Integer groupId, String privateKey) {
        super();
        this.userId = userId;
        this.groupId = groupId;
        this.privateKey = privateKey;
    }

}
