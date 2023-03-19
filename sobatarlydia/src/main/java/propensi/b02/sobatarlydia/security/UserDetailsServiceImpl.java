package propensi.b02.sobatarlydia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import propensi.b02.sobatarlydia.model.PenggunaModel;
import propensi.b02.sobatarlydia.repository.UserDB;

import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDB userDb;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DisabledException {
        Optional<PenggunaModel> pengguna = userDb.findByEmail(username);
        PenggunaModel user = new PenggunaModel();
       
        if (pengguna.isPresent()){
            user = pengguna.get();
        } 
        
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        if (user.getIsActive() == 0) {
            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
        }
        else {
            grantedAuthorities.add(new SimpleGrantedAuthority("Nonaktif"));
            return new User(user.getEmail(), user.getPassword(), false, true, true, true, grantedAuthorities);
        }
    }
}
