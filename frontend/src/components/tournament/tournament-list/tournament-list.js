import React from 'react';
import TournamentCard from '../tournament-card/tournament-card';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import "./tournament-list.scss";

const TournamentList = ({ tournaments }) => {
    return (
        <Container className="tournaments-list-container">
            <Row className="justify-content-center">
                {tournaments.map(tournament => (
                    <Col key={tournament.id} xs={12} md={6} className="mb-3">
                        <TournamentCard {...tournament} />
                    </Col>
                ))}
            </Row>
        </Container>
    );
};

export default TournamentList;
