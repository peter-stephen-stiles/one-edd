package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.RaceThiefAbilityBonus;
import com.nobbysoft.first.common.entities.staticdto.RaceThiefAbilityBonusKey;
import com.nobbysoft.first.common.servicei.RaceThiefAbilityBonusService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.RaceThiefAbilityBonusDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class RaceThiefAbilityBonusServiceImpl implements RaceThiefAbilityBonusService {

	private final ConnectionManager cm;
	private final RaceThiefAbilityBonusDAO dao;

	public RaceThiefAbilityBonusServiceImpl() {
		cm = new ConnectionManager();
		dao = new RaceThiefAbilityBonusDAO();
	}

	@Override
	public void createTable() throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				dao.createTable(con);
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public RaceThiefAbilityBonus get(RaceThiefAbilityBonusKey key) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.get(con, key);
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public void insert(RaceThiefAbilityBonus value) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				con.setAutoCommit(false);
				dao.insert(con, value);
				con.commit();
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public List<RaceThiefAbilityBonus> getList() throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.getList(con);

			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public List<RaceThiefAbilityBonus> getFilteredList(String filter) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.getFilteredList(con, filter);
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public void delete(RaceThiefAbilityBonus value) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				con.setAutoCommit(false);
				dao.delete(con, value);
				con.commit();
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public void update(RaceThiefAbilityBonus value) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				con.setAutoCommit(false);
				dao.update(con, value);
				con.commit();
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public List<CodedListItem<RaceThiefAbilityBonusKey>> getAsCodedList() throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.getAsCodedList(con);
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public List<RaceThiefAbilityBonus> getBonusesForRace(String raceId) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.getBonusesForRace(con, raceId);
			} finally {
				con.rollback();
			}
		}
	}

}
