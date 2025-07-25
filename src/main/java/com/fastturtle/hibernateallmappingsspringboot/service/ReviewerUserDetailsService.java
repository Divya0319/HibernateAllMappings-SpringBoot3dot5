package com.fastturtle.hibernateallmappingsspringboot.service;

import com.fastturtle.hibernateallmappingsspringboot.entity.Reviewer;
import com.fastturtle.hibernateallmappingsspringboot.repository.CoderRepository;
import com.fastturtle.hibernateallmappingsspringboot.repository.DesignerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewerUserDetailsService implements UserDetailsService {

    private final CoderRepository coderRepository;
    private final DesignerRepository designerRepository;

    private String authority;

    public ReviewerUserDetailsService(CoderRepository coderRepository, DesignerRepository designerRepository) {
        this.coderRepository = coderRepository;
        this.designerRepository = designerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Reviewer reviewer = null;

        Optional<? extends Reviewer> coder = coderRepository.findByEmail(email);

        if(coder.isPresent()) {
            reviewer = coder.get();
            authority = "ROLE_CODER";
        } else {
            Optional<? extends Reviewer> designer = designerRepository.findByEmail(email);
            if(designer.isPresent()) {
                reviewer = designer.get();
                authority = "ROLE_DESIGNER";
            }
        }

        if(reviewer == null) {
            throw new UsernameNotFoundException("Reviewer not found with email: " + email);
        }

        return new User(
                reviewer.getEmail(),
                reviewer.getPassword(),
                List.of(new SimpleGrantedAuthority(authority))
        );
    }
}
