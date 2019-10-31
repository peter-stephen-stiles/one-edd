package com.nobbysoft.first.server.service;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.pc.PlayerCharacterHp;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterHpKey;
import com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.first.common.servicei.PlayerCharacterHpService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.views.ViewPlayerCharacterHp;
import com.nobbysoft.first.server.dao.PlayerCharacterHpDAO;
import com.nobbysoft.first.server.dao.SpellDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class PlayerCharacterHpServiceImpl implements PlayerCharacterHpService {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private final ConnectionManager cm;
	private final PlayerCharacterHpDAO dao;
	private final SpellDAO spellDao;

	public PlayerCharacterHpServiceImpl() {
		cm = new ConnectionManager();
		dao = new PlayerCharacterHpDAO();
		spellDao = new SpellDAO();
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
	public PlayerCharacterHp get(PlayerCharacterHpKey key) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.get(con, key);
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public void insert(PlayerCharacterHp value) throws SQLException {
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
	public List<PlayerCharacterHp> getList() throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.getList(con);
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public List<PlayerCharacterHp> getFilteredList(String filter) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.getFilteredList(con, filter);
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public void delete(PlayerCharacterHp value) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				con.setAutoCommit(false);
				dao.delete(con, value);
				//
				con.commit();
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public void update(PlayerCharacterHp value) throws SQLException {
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
	public List<CodedListItem<PlayerCharacterHpKey>> getAsCodedList() throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.getAsCodedList(con);
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public List<PlayerCharacterHp> getForPC(int pcId) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.getForPC(con, pcId);
			} finally {
				con.rollback();
			}
		}
	}

	public List<ViewPlayerCharacterHp> getViewForPC(int pcId) throws SQLException {
		try (Connection con = cm.getConnection()) {
			return dao.getViewForPC(con, pcId);
		}
	}

	public void addAll(List<PlayerCharacterHp> list) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				for (PlayerCharacterHp dto : list) {
					dao.insert(con, dto);
				}
				con.commit();
			} finally {
				con.rollback();
			}
		}
	}

}
