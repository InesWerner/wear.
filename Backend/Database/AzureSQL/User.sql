SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[userID] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](240) NOT NULL,
	[name] [varchar](240) NOT NULL,
	[mail] [varchar](400) NOT NULL,
	[password] [varchar](200) NULL,
	[profilImage] [varbinary](max) NULL,
	[profilImageString] [varchar](max) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[User] ADD PRIMARY KEY CLUSTERED 
(
	[userID] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY]
GO
