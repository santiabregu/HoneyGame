export function PointsDisplay({ points }) {
    const pointsStyle = {
        position: 'fixed',
        top: '70px',
        right: '20px',
        backgroundColor: 'rgba(255, 255, 255, 0.8)',
        padding: '10px',
        borderRadius: '8px',
        border: '2px solid black',
        zIndex: 1000,
        fontSize: '20px',
        fontWeight: 'bold'
    };

    return (
        <div style={pointsStyle}>
            Points: {points}
        </div>
    );
}