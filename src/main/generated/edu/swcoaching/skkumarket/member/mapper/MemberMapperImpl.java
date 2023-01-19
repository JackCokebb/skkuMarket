package edu.swcoaching.skkumarket.member.mapper;

import edu.swcoaching.skkumarket.member.dto.MemberDto;
import edu.swcoaching.skkumarket.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-19T20:05:57+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 17.0.5 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostToMember(MemberDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member member = new Member();

        member.setUsername( requestBody.getUsername() );
        member.setEmail( requestBody.getEmail() );
        member.setPassword( requestBody.getPassword() );

        return member;
    }

    @Override
    public Member memberPutToMember(MemberDto.Put requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member member = new Member();

        member.setId( requestBody.getId() );
        member.setUsername( requestBody.getUsername() );
        member.setEmail( requestBody.getEmail() );
        member.setPassword( requestBody.getPassword() );
        member.setStatus( requestBody.getStatus() );

        return member;
    }

    @Override
    public MemberDto.Response memberToMemberResponse(Member member) {
        if ( member == null ) {
            return null;
        }

        long id = 0L;
        String username = null;
        String email = null;
        Member.Status status = null;

        if ( member.getId() != null ) {
            id = member.getId();
        }
        username = member.getUsername();
        email = member.getEmail();
        status = member.getStatus();

        MemberDto.Response response = new MemberDto.Response( id, username, email, status );

        return response;
    }

    @Override
    public List<MemberDto.Response> membersToMemberResponses(List<Member> members) {
        if ( members == null ) {
            return null;
        }

        List<MemberDto.Response> list = new ArrayList<MemberDto.Response>( members.size() );
        for ( Member member : members ) {
            list.add( memberToMemberResponse( member ) );
        }

        return list;
    }
}
