/*
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
package com.webank.webase.node.mgr.node;

import com.alibaba.fastjson.JSON;
import com.webank.webase.node.mgr.base.entity.BasePageResponse;
import com.webank.webase.node.mgr.base.entity.BaseResponse;
import com.webank.webase.node.mgr.base.entity.ConstantCode;
import com.webank.webase.node.mgr.base.enums.DataStatus;
import com.webank.webase.node.mgr.base.enums.NodeType;
import com.webank.webase.node.mgr.base.exception.NodeMgrException;
import com.webank.webase.node.mgr.scheduler.CheckNodeTask;
import com.webank.webase.node.mgr.scheduler.SharedChainInfoTask;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for node data.
 */
@Log4j2
@RestController
@RequestMapping("node")
public class NodeController {

    @Autowired
    private NodeService nodeService;
    @Autowired
    private SharedChainInfoTask sharedChainInfoTask;
    @Autowired
    private CheckNodeTask checkNodeTask;

    /**
     * qurey node info list.
     */
    @GetMapping(value = "/nodeList/{networkId}/{pageNumber}/{pageSize}")
    public BasePageResponse queryNodeList(@PathVariable("networkId") Integer networkId,
        @PathVariable("pageNumber") Integer pageNumber,
        @PathVariable("pageSize") Integer pageSize,
        @RequestParam(value = "nodeName", required = false) String nodeName)
        throws NodeMgrException {
        BasePageResponse pagesponse = new BasePageResponse(ConstantCode.SUCCESS);
        Instant startTime = Instant.now();
        log.info(
            "start queryNodeList startTime:{} networkId:{}  pageNumber:{} pageSize:{} nodeName:{}",
            startTime.toEpochMilli(), networkId, pageNumber,
            pageSize, nodeName);

        // share from chain
        //sharedChainInfoTask.asyncShareFromChain(networkId, ShareType.NODE);

        // param
        NodeParam queryParam = new NodeParam();
        queryParam.setNetworkId(networkId);
        queryParam.setPageSize(pageSize);
        queryParam.setNodeName(nodeName);

        Integer count = nodeService.countOfNode(queryParam);
        if (count != null && count > 0) {
            Integer start = Optional.ofNullable(pageNumber).map(page -> (page - 1) * pageSize)
                .orElse(null);
            queryParam.setStart(start);

            int queryTimes = 0;// if current node is invalid, try again
            List<TbNode> listOfnode = null;
            while (true) {
                listOfnode = nodeService.qureyNodeList(queryParam);
                long countOfInvalid = listOfnode.parallelStream()
                    .filter(node -> (NodeType.CURRENT.getValue() == node.getNodeType()
                        && DataStatus.NORMAL.getValue() != node.getNodeActive())).count();
                if (countOfInvalid > 0 && queryTimes == 0) {
                    queryTimes += 1;
                    checkNodeTask.checkNodeStatus();
                    continue;
                } else {
                    break;
                }
            }
            pagesponse.setData(listOfnode);
            pagesponse.setTotalCount(count);
        }

        log.info("end queryNodeList useTime:{} result:{}",
            Duration.between(startTime, Instant.now()).toMillis(), JSON.toJSONString(pagesponse));
        return pagesponse;
    }

    /**
     * get node info.
     */
    @GetMapping(value = "/nodeInfo/{networkId}")
    public BaseResponse getNodeInfo(@PathVariable("networkId") Integer networkId,
        @RequestParam(value = "nodeType", required = true) Integer nodeType)
        throws NodeMgrException {

        Instant startTime = Instant.now();
        log.info("start addNodeInfo startTime:{} networkId:{} nodeType:{}",
            startTime.toEpochMilli(), networkId, nodeType);

        // param
        NodeParam param = new NodeParam();
        param.setNetworkId(networkId);
        param.setNodeType(nodeType);

        // query node row
        TbNode tbNode = nodeService.queryNodeInfo(param);

        BaseResponse baseResponse = new BaseResponse(ConstantCode.SUCCESS);
        baseResponse.setData(tbNode);

        log.info("end addNodeInfo useTime:{} result:{}",
            Duration.between(startTime, Instant.now()).toMillis(), JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * add node info.
     */
    @PostMapping(value = "/nodeInfo")
    public BaseResponse addNodeInfo(@RequestBody Node node) throws NodeMgrException {
        BaseResponse baseResponse = new BaseResponse(ConstantCode.SUCCESS);
        Instant startTime = Instant.now();
        log.info("start addNodeInfo startTime:{} Node:{}", startTime.toEpochMilli(),
            JSON.toJSONString(node));

        // add node ip
        Integer nodeId = nodeService.addNodeInfo(node);
        // query node row
        TbNode tbNode = nodeService.queryByNodeId(nodeId);
        baseResponse.setData(tbNode);

        log.info("end addNodeInfo useTime:{} result:{}",
            Duration.between(startTime, Instant.now()).toMillis(), JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * delete node info.
     */
    @DeleteMapping(value = "/nodeInfo/{nodeId}")
    public BaseResponse deleteNode(@PathVariable("nodeId") Integer nodeId) throws NodeMgrException {
        BaseResponse baseResponse = new BaseResponse(ConstantCode.SUCCESS);
        Instant startTime = Instant.now();
        log.info("start deleteNode. startTime:{} nodeId:{}", startTime.toEpochMilli(), nodeId);

        nodeService.deleteByNodeId(nodeId);

        log.info("end deleteNode. useTime:{} result:{}",
            Duration.between(startTime, Instant.now()).toMillis(), JSON.toJSONString(baseResponse));
        return baseResponse;
    }

}