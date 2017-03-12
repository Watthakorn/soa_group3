package reserv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reserv.exeption.AlreadyReservException;
import reserv.exeption.ReservNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ReservRepository {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Transactional(readOnly = true)
    public Reserv getReservByID(int reserv_id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM reserv WHERE reserv_id=?",
                    new Object[]{reserv_id},new ReservRowMapper());
        }catch (Exception ex){
            throw new ReservNotFoundException(reserv_id);
        }
    }

    @Transactional(readOnly = true)
    public List<Reserv> findByPage(int page, int itemPerPage) {
        int offset = (page-1) * itemPerPage;
        return this.jdbcTemplate.query("SELECT * FROM reserv WHERE reserv_id LIMIT ? OFFSET ?",
                new Object[]{itemPerPage,offset},new ReservRowMapper());
    }

    @Transactional(readOnly = true)
    public List<Reserv> findByFilter(String date,String user) {
        if(date == null && user == null) {
            LocalDate localDate = LocalDate.now();
            return this.jdbcTemplate.query("SELECT * FROM reserv WHERE reserv_date>?",
                    new Object[]{dtf.format(localDate)}, new ReservRowMapper());
        }
        else if(date != null && user != null){
            return this.jdbcTemplate.query("SELECT * FROM reserv WHERE reserv_date=? AND reserv_user=?",
                    new Object[]{date,user}, new ReservRowMapper());
        }else if(date != null){
            return this.jdbcTemplate.query("SELECT * FROM reserv WHERE reserv_date=?",
                    new Object[]{date}, new ReservRowMapper());
        }else{
            return this.jdbcTemplate.query("SELECT * FROM reserv WHERE reserv_user=?",
                    new Object[]{user}, new ReservRowMapper());
        }
    }

    @Transactional
    public Reserv doReserv(Reserv reserv){
        String sql = "INSERT INTO RESERV" +
                "(reserv_user, reserv_field_id, reserv_ex_id, reserv_time, reserv_date) " +
                "VALUE(?,?,?,?,?);";
        try {
            this.jdbcTemplate.update(sql, reserv.getReserv_user(), reserv.getReserv_field_id(),
                    reserv.getReserv_ex_id(), reserv.getReserv_time(), reserv.getReserv_date());

            return this.jdbcTemplate.queryForObject("SELECT * FROM reserv WHERE reserv_field_id=? " +
                            "AND reserv_ex_id=? AND reserv_time=? AND reserv_date=? ",
                    new Object[]{reserv.getReserv_field_id(), reserv.getReserv_ex_id(),
                            reserv.getReserv_time(), reserv.getReserv_date()}, new ReservRowMapper());
        }catch (Exception ex){
            throw new AlreadyReservException(reserv);
        }

    }

    @Transactional
    public Reserv confirmReserv(int reserv_id){
        String sql = "UPDATE reserv SET reserv_status='confirm' WHERE reserv_id=?;";
        try {
            this.jdbcTemplate.update(sql,reserv_id);
            return this.jdbcTemplate.queryForObject("SELECT * FROM reserv WHERE reserv_id=?"
                , new Object[]{reserv_id},new ReservRowMapper());
        }catch (Exception ex){
            throw new ReservNotFoundException(reserv_id);
        }
    }

    @Transactional
    public void deleteReserv(int reserv_id) {
        String sql = "DELETE FROM reserv WHERE reserv_id=?;";
        try {
            this.jdbcTemplate.update(sql, reserv_id);
        }catch (Exception ex){
            throw new ReservNotFoundException(reserv_id);
        }
    }
}
