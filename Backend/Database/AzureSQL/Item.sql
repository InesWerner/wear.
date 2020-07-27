SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Item](
	[itemID] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NULL,
	[category] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[numberOfUsage] [int] NULL,
	[status] [int] NULL,
	[size] [varchar](255) NULL,
	[color] [varchar](255) NULL,
	[tagID] [bigint] NULL,
	[brand] [varchar](255) NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Item] ADD PRIMARY KEY CLUSTERED 
(
	[itemID] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Item]  WITH CHECK ADD FOREIGN KEY([tagID])
REFERENCES [dbo].[Tag] ([tagId])
GO
