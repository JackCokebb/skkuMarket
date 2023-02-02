package edu.swcoaching.skkumarket.member.mapper;

import edu.swcoaching.skkumarket.member.dto.MemberDto;
import edu.swcoaching.skkumarket.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-02T22:11:33+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 17.0.5 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostToMember(MemberDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.email( requestBody.getEmail() );
        member.password( requestBody.getPassword() );

        return member.build();
    }

    @Override
    public Member memberPutToMember(MemberDto.Put requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.id( requestBody.getId() );
        member.email( requestBody.getEmail() );
        member.password( requestBody.getPassword() );
        member.status( requestBody.getStatus() );

        return member.build();
    }

    @Override
    public MemberDto.Response memberToMemberResponse(Member member) {
        if ( member == null ) {
            return null;
        }

        long id = 0L;
        String email = null;
        Member.Status status = null;

        if ( member.getId() != null ) {
            id = member.getId();
        }
        email = member.getEmail();
        status = member.getStatus();

        String username = null;

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
