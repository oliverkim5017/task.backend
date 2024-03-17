package org.task.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.task.backend.mapper.TeamMapper;
import org.task.backend.model.entity.Team;
import org.task.backend.service.TeamService;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

}
